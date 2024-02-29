package com.example.xiao.logmanager.server.user.service.data;

import com.example.xiao.logmanager.server.common.enums.RoleEnum;
import com.example.xiao.logmanager.server.user.entity.po.SysUserAppRolePo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xiaozhiwei
 * @since 2024-02-24
 */
public interface SysUserAppRoleService extends IService<SysUserAppRolePo> {

    List<String> getRolesByUserId(Long userId);

    List<String> getRolesByUserIdAndAppId(Long userId,Long appId);

    void addRoleBatch(Long userId, List<RoleEnum> roles);

    void addRoleBatch(Long userId, Long appId, List<RoleEnum> roles);
}
