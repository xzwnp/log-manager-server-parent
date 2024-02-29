package com.example.xiao.logmanager.server.user.service.data;

import com.example.xiao.logmanager.server.user.entity.po.SysUserPo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统用户信息表 服务类
 * </p>
 *
 * @author xiaozhiwei
 * @since 2024-02-24
 */
public interface SysUserService extends IService<SysUserPo> {

    SysUserPo findByUsername(String username);
}
