package com.example.xiao.logmanager.server.user.service.data.impl;

import com.example.xiao.logmanager.server.user.entity.po.SysUserPo;
import com.example.xiao.logmanager.server.user.dao.SysUserDao;
import com.example.xiao.logmanager.server.user.service.data.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Collections2;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public SysUserPo getByUsername(String username) {
        return this.lambdaQuery().eq(SysUserPo::getUsername, username).one();
    }

    @Override
    public Map<Long, String> queryUsernamesBatch(List<Long> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return new HashMap<>();
        }
        return this.lambdaQuery().in(SysUserPo::getId, userIds).list()
                .stream().collect(Collectors.toMap(SysUserPo::getId, SysUserPo::getUsername));
    }
}
