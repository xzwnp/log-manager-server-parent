package com.example.xiao.logmanager.server.user.service.data.impl;

import com.example.xiao.logmanager.server.common.enums.RoleEnum;
import com.example.xiao.logmanager.server.user.entity.po.SysUserAppRolePo;
import com.example.xiao.logmanager.server.user.dao.SysUserAppRoleDao;
import com.example.xiao.logmanager.server.user.service.data.SysUserAppRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xiaozhiwei
 * @since 2024-02-24
 */
@Service
public class SysUserAppRoleServiceImpl extends ServiceImpl<SysUserAppRoleDao, SysUserAppRolePo> implements SysUserAppRoleService {
    private static final long SYSTEM_APP_ID = -1L;

    @Override
    public List<String> getRolesByUserId(Long userId) {
        return this.getRolesByUserIdAndAppId(userId, SYSTEM_APP_ID);
    }

    @Override
    public List<String> getRolesByUserIdAndAppId(Long userId, Long appId) {
        return this.lambdaQuery().eq(SysUserAppRolePo::getUserId, userId).eq(SysUserAppRolePo::getAppId, appId).list() //userAppRolePo List
                .stream().map(SysUserAppRolePo::getRoleId) //map to RoleId
                .map(roleId -> RoleEnum.of(roleId.intValue())) //map to RoleEnum
                .map(RoleEnum::getName) //map to roleName
                .toList();
    }

    @Override
    @Transactional
    public void addRoleBatch(Long userId, List<RoleEnum> roles) {
        addRoleBatch(userId, SYSTEM_APP_ID, roles);
    }

    @Override
    @Transactional
    public void addRoleBatch(Long userId, Long appId, List<RoleEnum> roles) {
        List<SysUserAppRolePo> poList = roles.stream()
                .map(RoleEnum::getCode) //roleIds
                .map(roleId -> new SysUserAppRolePo().setUserId(userId).setAppId(appId).setRoleId(Long.valueOf(roleId)))
                .toList();
        this.saveBatch(poList);
    }
}
