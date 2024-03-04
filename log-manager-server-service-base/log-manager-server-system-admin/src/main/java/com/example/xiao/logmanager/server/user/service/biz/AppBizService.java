package com.example.xiao.logmanager.server.user.service.biz;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.xiao.logmanager.api.feign.LogAppFeignClient;
import com.example.xiao.logmanager.server.common.entity.resp.PageDto;
import com.example.xiao.logmanager.server.common.entity.resp.R;
import com.example.xiao.logmanager.server.common.enums.ResultCode;
import com.example.xiao.logmanager.server.common.enums.RoleEnum;
import com.example.xiao.logmanager.server.common.exception.BizException;
import com.example.xiao.logmanager.server.common.util.JsonUtil;
import com.example.xiao.logmanager.server.common.util.UserThreadLocalUtil;
import com.example.xiao.logmanager.server.user.entity.coverter.SysAppConverter;
import com.example.xiao.logmanager.server.user.entity.po.SysAppPo;
import com.example.xiao.logmanager.server.user.entity.po.SysUserAppRolePo;
import com.example.xiao.logmanager.server.user.entity.po.SysUserPo;
import com.example.xiao.logmanager.server.user.entity.req.QueryAppReq;
import com.example.xiao.logmanager.server.user.entity.req.UpdateAppInfoReq;
import com.example.xiao.logmanager.server.user.entity.resp.QueryAppResp;
import com.example.xiao.logmanager.server.user.service.data.SysAppService;
import com.example.xiao.logmanager.server.user.service.data.SysUserAppRoleService;
import com.example.xiao.logmanager.server.user.service.data.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppBizService {
    private final LogAppFeignClient logAppFeignClient;

    private final SysAppService sysAppService;

    private final SysUserService sysUserService;

    private final SysUserAppRoleService userAppRoleService;

    private final SysAppConverter sysAppConverter;
    private final UserPermissionService permissionService;

    public PageDto<QueryAppResp> queryApps(QueryAppReq req) {
        PageDto<QueryAppResp> resp = new PageDto<>(req);
        //系统管理员能看到全部应用,非系统管理员只能看到自己管理的应用
        List<Long> availableAppIds = null;
        if (!UserThreadLocalUtil.containsRole(RoleEnum.SYS_ADMIN)) {
            availableAppIds = userAppRoleService.lambdaQuery()
                    .eq(SysUserAppRolePo::getUserId, UserThreadLocalUtil.getUserId())
                    .eq(SysUserAppRolePo::getRoleId, RoleEnum.APP_ADMIN.getCode())
                    .list()
                    .stream().map(SysUserAppRolePo::getAppId).toList();
            if (availableAppIds.isEmpty()) {
                return resp;
            }
        }

        Page<SysAppPo> sysAppPoPage = sysAppService.lambdaQuery()
                .eq(StringUtils.isNotBlank(req.getAppName()), SysAppPo::getAppName, req.getAppName())
                .in(availableAppIds != null, SysAppPo::getId, availableAppIds)
                .page(new Page<>(req.getCurrent(), req.getSize()));
        resp.setTotal(sysAppPoPage.getTotal());
        List<SysAppPo> sysAppPoList = sysAppPoPage.getRecords();
        List<QueryAppResp> queryAppRespList = sysAppPoList.stream().map(sysAppConverter::sysAppPo2queryAppResp).toList();
        resp.setRecords(queryAppRespList);
        return resp;
    }

    public QueryAppResp queryAppDetail(String appName) {
        SysAppPo app = sysAppService.getByAppName(appName);
        QueryAppResp queryAppResp = sysAppConverter.sysAppPo2queryAppResp(app);
        //查appUser
        List<SysUserAppRolePo> uarList = userAppRoleService.lambdaQuery().eq(SysUserAppRolePo::getAppId, app.getId()).list();

        //userid->username
        List<Long> userIds = uarList.stream().map(SysUserAppRolePo::getUserId).toList();
        Map<Long, String> userIdMap = sysUserService.queryUsernamesBatch(userIds);

        //根据role分组,得出app各角色都有哪些用户
        Map<Long, Set<String>> roleUsernameMap = uarList.stream().collect(Collectors.groupingBy(SysUserAppRolePo::getRoleId, mapping(uar -> userIdMap.get(uar.getUserId()), toSet())));

        //组装
        queryAppResp.setAdmins(roleUsernameMap.getOrDefault(RoleEnum.APP_ADMIN.getCode().longValue(), new HashSet<>()));
        queryAppResp.setUsers(roleUsernameMap.getOrDefault(RoleEnum.APP_USER.getCode().longValue(), new HashSet<>()));

        return queryAppResp;
    }

    @Cacheable(cacheNames = "appAdmin", key = "'list::'+#username")
    public Map<String, Set<String>> listApps(String username) {
        List<SysAppPo> appPos;
        //系统管理员能看到全部应用,非系统管理员只能看到自己管理的应用
        if (UserThreadLocalUtil.containsRole(RoleEnum.SYS_ADMIN)) {
            appPos = sysAppService.lambdaQuery().list();
        } else {
            List<Long> availableAppIds = null;
            availableAppIds = userAppRoleService.lambdaQuery()
                    .eq(SysUserAppRolePo::getUserId, UserThreadLocalUtil.getUserId())
                    .eq(SysUserAppRolePo::getRoleId, RoleEnum.APP_ADMIN.getCode())
                    .list()
                    .stream().map(SysUserAppRolePo::getAppId).toList();
            if (availableAppIds.isEmpty()) {
                return new HashMap<>();
            }
            appPos = sysAppService.lambdaQuery().in(SysAppPo::getId, availableAppIds).list();
        }
        return appPos.stream().collect(Collectors.toMap(SysAppPo::getAppName, SysAppPo::getGroups,
                (s1, s2) -> {
                    s1.addAll(s2);
                    return s1;
                }
        ));
    }

    @Transactional
    @CacheEvict(cacheNames = "appAdmin", allEntries = true)
    public void importApps() {
        R<Map<String, Set<String>>> resp = logAppFeignClient.exportApps();
        if (!resp.isSuccess()) {
            //rpc fail
            throw new BizException(ResultCode.RPC_EXCEPTION, "无法获取应用信息");
        }
        //查询已有应用信息
        List<SysAppPo> appPos = sysAppService.list();
        Map<String, SysAppPo> appPoMap = appPos.stream().collect(toMap(SysAppPo::getAppName, o -> o));

        Map<String, Set<String>> appGroupMap = resp.getData();
        log.info("app-group info:{}", JsonUtil.toJson(appGroupMap));
        for (Map.Entry<String, Set<String>> appGroup : appGroupMap.entrySet()) {
            String appName = appGroup.getKey();
            Set<String> groups = appGroup.getValue();
            //如果应用已经存在,就更新,否则插入
            SysAppPo sysAppPo = appPoMap.get(appName);
            if (sysAppPo == null) {
                sysAppPo = new SysAppPo();
                sysAppPo.setAppName(appName);
                sysAppPo.setGroups(groups);
                appPos.add(sysAppPo);
            }
        }
        //存入数据库
        sysAppService.saveOrUpdateBatch(appPos);

        //导入应用的系统管理员自动获取这些应用的管理权限
        List<Long> appIds = appPos.stream().map(SysAppPo::getId).toList();
        userAppRoleService.lambdaUpdate()
                .eq(SysUserAppRolePo::getUserId, UserThreadLocalUtil.getUserId())
                .in(SysUserAppRolePo::getAppId, appIds)
                .remove();
        List<SysUserAppRolePo> sysUserAppRolePos = appIds.stream().map(appId -> new SysUserAppRolePo(UserThreadLocalUtil.getUserId(), appId, RoleEnum.APP_ADMIN.getCode().longValue()))
                .toList();
        userAppRoleService.saveBatch(sysUserAppRolePos);
        List<SysUserAppRolePo> sysUserAppRolePos2 = appIds.stream().map(appId -> new SysUserAppRolePo(UserThreadLocalUtil.getUserId(), appId, RoleEnum.APP_USER.getCode().longValue()))
                .toList();
        userAppRoleService.saveBatch(sysUserAppRolePos2);
    }

    @CacheEvict(cacheNames = "appAdmin", allEntries = true)
    public void deleteApp(String appName) {
        SysAppPo sysAppPo = sysAppService.getByAppName(appName);
        if (sysAppPo == null) {
            throw new BizException(ResultCode.DATA_NOT_EXIST, "应用不存在!");
        }
        sysAppService.removeById(sysAppPo.getId());
    }

    /**
     * @param username 用户名
     * @param appName  应用名称
     * @param roleEnum 要添加/删除的角色
     * @param isAdd    添加还是删除角色
     */
    @CacheEvict(cacheNames = "appAdmin", allEntries = true)
    public void modifyAppUser(String username, String appName, RoleEnum roleEnum, boolean isAdd) {
        List<RoleEnum> roles;
        switch (roleEnum) { //添加/删除应用管理员时,需要同步添加/删除应用成员的身份
            case APP_USER -> roles = List.of(RoleEnum.APP_USER);
            case APP_ADMIN -> roles = List.of(RoleEnum.APP_USER, RoleEnum.APP_ADMIN);
            default -> throw new BizException("不支持的角色");
        }
        SysAppPo app = sysAppService.getByAppName(appName);
        if (app == null) {
            throw new BizException(ResultCode.DATA_NOT_EXIST, "应用不存在!");
        }
        SysUserPo user = sysUserService.getByUsername(username);
        if (user == null) {
            throw new BizException(ResultCode.DATA_NOT_EXIST, "用户不存在!");
        }
        //权限校验
        permissionService.validateRole(RoleEnum.APP_ADMIN, UserThreadLocalUtil.getUserId(), app.getId());


        //添加角色
        if (isAdd) {
            //删除用户在该应用中已存在的角色,避免重复添加
            userAppRoleService.lambdaUpdate()
                    .eq(SysUserAppRolePo::getUserId, user.getId())
                    .eq(SysUserAppRolePo::getAppId, app.getId())
                    .remove();
            List<SysUserAppRolePo> sysUserAppRolePos = roles.stream().map(role -> new SysUserAppRolePo(user.getId(), app.getId(), role.getCode().longValue())).toList();
            userAppRoleService.saveBatch(sysUserAppRolePos);
        } else {
            //删除角色
            List<Integer> roleIds = roles.stream().map(RoleEnum::getCode).collect(toList());
            userAppRoleService.lambdaUpdate()
                    .eq(SysUserAppRolePo::getUserId, user.getId())
                    .eq(SysUserAppRolePo::getAppId, app.getId())
                    .in(SysUserAppRolePo::getRoleId, roleIds)
                    .remove();
        }
    }

    public void modifyAppInfo(UpdateAppInfoReq req) {
        SysAppPo app = sysAppService.getByAppName(req.getAppName());
        if (app == null) {
            throw new BizException(ResultCode.DATA_NOT_EXIST, "应用不存在!");
        }
        //权限校验
        permissionService.validateRole(RoleEnum.APP_ADMIN, UserThreadLocalUtil.getUserId(), app.getId());

        app.setDescription(req.getDescription());
        sysAppService.updateById(app);
    }
}
