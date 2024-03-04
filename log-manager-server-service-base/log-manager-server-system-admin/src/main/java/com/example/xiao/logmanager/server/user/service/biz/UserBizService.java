package com.example.xiao.logmanager.server.user.service.biz;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.xiao.logmanager.server.common.entity.resp.PageDto;
import com.example.xiao.logmanager.server.common.enums.ResultCode;
import com.example.xiao.logmanager.server.common.enums.RoleEnum;
import com.example.xiao.logmanager.server.common.exception.BizException;
import com.example.xiao.logmanager.server.common.util.MD5Util;
import com.example.xiao.logmanager.server.common.util.UserThreadLocalUtil;
import com.example.xiao.logmanager.server.common.util.jwt.JwtEntity;
import com.example.xiao.logmanager.server.common.util.jwt.JwtUtil;
import com.example.xiao.logmanager.server.user.constant.SystemConstants;
import com.example.xiao.logmanager.server.user.entity.po.SysUserAppRolePo;
import com.example.xiao.logmanager.server.user.entity.po.SysUserPo;
import com.example.xiao.logmanager.server.user.entity.req.*;
import com.example.xiao.logmanager.server.user.entity.resp.AddUserResp;
import com.example.xiao.logmanager.server.user.entity.resp.RefreshTokenResp;
import com.example.xiao.logmanager.server.user.entity.resp.UserInfoResp;
import com.example.xiao.logmanager.server.user.entity.resp.UserLoginResp;
import com.example.xiao.logmanager.server.user.service.data.SysUserAppRoleService;
import com.example.xiao.logmanager.server.user.service.data.SysUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserBizService {
    private final SysUserService sysUserService;

    private final SysUserAppRoleService userAppRoleService;

    public UserLoginResp login(UserLoginReq req) {
        SysUserPo user = sysUserService.getByUsername(req.getUsername());
        if (user == null) {
            throw new BizException(ResultCode.DATA_NOT_EXIST, "用户不存在");
        }
        //校验密码
        if (!MD5Util.hashWithSalt(req.getPassword()).equals(user.getPassword())) {
            throw new BizException(ResultCode.BIZ_EXCEPTION, "密码错误");
        }
        //查询角色
        List<String> roles = userAppRoleService.getRolesByUserId(user.getId());
        //生成token
        LocalDateTime expire = LocalDateTime.now().plus(JwtUtil.ACCESS_TOKEN_EXPIRE, ChronoUnit.MILLIS);
        JwtEntity jwtEntity = new JwtEntity().setUserId(user.getId()).setUsername(user.getUsername()).setNickname(user.getNickname()).setRoles(roles);
        String accessToken = JwtUtil.createAccessToken(jwtEntity);
        String refreshToken = JwtUtil.createRefreshToken(jwtEntity);
        return new UserLoginResp(user.getUsername(), roles, accessToken, refreshToken, expire);
    }

    public RefreshTokenResp refreshToken(RefreshTokenReq req) {
        String refreshToken = req.getRefreshToken();
        LocalDateTime expire = LocalDateTime.now().plus(JwtUtil.ACCESS_TOKEN_EXPIRE, ChronoUnit.MILLIS);
        JwtEntity jwtEntity = JwtUtil.getUserInfo(refreshToken);
        String accessToken = JwtUtil.createAccessToken(jwtEntity);
        return new RefreshTokenResp(accessToken, refreshToken, expire);
    }

    @Transactional
    public AddUserResp addUser(AddUserReq req) {
        List<RoleEnum> roles = req.getRoles();
        String username = req.getUsername();
        SysUserPo user = sysUserService.getByUsername(username);
        if (user != null) {
            throw new BizException(ResultCode.DATA_NOT_EXIST, "用户已存在!");
        }
        //创建用户
        String password = MD5Util.hashWithSalt(AddUserReq.defaultPassword);
        user = new SysUserPo().setUsername(username).setNickname(username).setPassword(password);
        sysUserService.save(user);
        //创建用户-角色关系
        userAppRoleService.addRoleBatch(user.getId(), roles);

        List<String> roleNames = roles.stream().map(RoleEnum::getName).toList();
        return new AddUserResp(username, AddUserReq.defaultPassword, roleNames);
    }

    @Transactional
    public void removeUser(String username) {
        SysUserPo user = sysUserService.getByUsername(username);
        if (user == null) {
            throw new BizException(ResultCode.DATA_NOT_EXIST, "用户不存在");
        }
        sysUserService.removeById(user.getId());
        userAppRoleService.lambdaUpdate().eq(SysUserAppRolePo::getUserId, user.getId()).remove();
    }

    public void changePassword(ChangePasswordReq req) {
        Long userId = UserThreadLocalUtil.getUserId();
        SysUserPo user = sysUserService.getById(userId);
        if (user == null) {
            throw new BizException(ResultCode.DATA_NOT_EXIST, "用户不存在");
        }
        //校验原密码
        if (!MD5Util.hashWithSalt(req.getOriginalPassword()).equals(user.getPassword())) {
            throw new BizException(ResultCode.BIZ_EXCEPTION, "原密码错误");
        }
        user.setPassword(MD5Util.hashWithSalt(req.getNewPassword()));
        sysUserService.updateById(user);
    }

    public PageDto<UserInfoResp> queryUser(QueryUserReq req) {
        PageDto<UserInfoResp> pageResp = new PageDto<>(req.getCurrent(), req.getSize());
        //page query users
        Page<SysUserPo> userPage = sysUserService.lambdaQuery()
                .eq(StringUtils.isNotBlank(req.getUsername()), SysUserPo::getUsername, req.getUsername()).page(new Page<>(req.getCurrent(), req.getSize()));
        pageResp.setTotal(userPage.getTotal());
        List<SysUserPo> userPoList = userPage.getRecords();

        //query roles
        List<Long> userIds = userPoList.stream().map(SysUserPo::getId).collect(Collectors.toList());
        Map<Long, List<SysUserAppRolePo>> userIdRoleMap = userAppRoleService.lambdaQuery()
                .eq(SysUserAppRolePo::getAppId, SystemConstants.SYSTEM_APP_ID).in(SysUserAppRolePo::getUserId, userIds)
                .orderByDesc(SysUserAppRolePo::getUserId).orderByDesc(SysUserAppRolePo::getRoleId).list()
                .stream().collect(Collectors.groupingBy(SysUserAppRolePo::getUserId));
        //build resp
        List<UserInfoResp> userList = userPoList.stream().map(userPo -> {
            List<String> roles = userIdRoleMap.getOrDefault(userPo.getId(), new ArrayList<>())
                    .stream().map(uar -> RoleEnum.of(uar.getRoleId().intValue()).getDesc())
                    .toList();
            return UserInfoResp.builder().id(userPo.getId()).username(userPo.getUsername()).nickname(userPo.getNickname()).avatarUrl(userPo.getAvatarUrl())
                    .roles(roles).build();
        }).toList();
        pageResp.setRecords(userList);

        return pageResp;
    }

    public void modifySysAdminRole(String username, Boolean isAdd) {
        SysUserPo user = sysUserService.getByUsername(username);
        if (user == null) {
            throw new BizException(ResultCode.DATA_NOT_EXIST, "用户不存在!");
        }
        List<String> roles = userAppRoleService.getRolesByUserIdAndAppId(user.getId(), SystemConstants.SYSTEM_APP_ID);
        if (isAdd) {
            if (roles.contains(RoleEnum.SYS_ADMIN.getName())) {
                throw new BizException(ResultCode.DATA_ALREADY_EXIST, "用户已经是系统管理员了!");
            }
            SysUserAppRolePo sysUserAppRolePo = new SysUserAppRolePo(user.getId(), SystemConstants.SYSTEM_APP_ID, RoleEnum.SYS_ADMIN.getCode().longValue());
            userAppRoleService.save(sysUserAppRolePo);
        } else {
            if (!roles.contains(RoleEnum.SYS_ADMIN.getName())) {
                throw new BizException(ResultCode.DATA_ALREADY_EXIST, "取消失败,用户并不是系统管理员!");
            }
            userAppRoleService.lambdaUpdate().eq(SysUserAppRolePo::getUserId, user.getId())
                    .eq(SysUserAppRolePo::getRoleId, RoleEnum.SYS_ADMIN.getCode()).remove();
        }
    }
}
