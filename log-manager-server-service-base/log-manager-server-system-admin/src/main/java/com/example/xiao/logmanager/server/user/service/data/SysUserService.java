package com.example.xiao.logmanager.server.user.service.data;

import com.example.xiao.logmanager.server.user.entity.po.SysUserPo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统用户信息表 服务类
 * </p>
 *
 * @author xiaozhiwei
 * @since 2024-02-24
 */
public interface SysUserService extends IService<SysUserPo> {

    SysUserPo getByUsername(String username);

    Map<Long, String> queryUsernamesBatch(List<Long> userIds);
}
