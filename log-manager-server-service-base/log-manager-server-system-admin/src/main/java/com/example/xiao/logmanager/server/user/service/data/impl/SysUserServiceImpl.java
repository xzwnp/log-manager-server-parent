package com.example.xiao.logmanager.server.user.service.data.impl;

import com.example.xiao.logmanager.server.user.entity.po.SysUserPo;
import com.example.xiao.logmanager.server.user.dao.SysUserDao;
import com.example.xiao.logmanager.server.user.service.data.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户信息表 服务实现类
 * </p>
 *
 * @author xiaozhiwei
 * @since 2024-02-24
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserPo> implements SysUserService {
    @Override
    public SysUserPo findByUsername(String username) {
        return this.lambdaQuery().eq(SysUserPo::getUsername, username).one();
    }
}
