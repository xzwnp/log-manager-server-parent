package com.example.xiao.logmanager.server.user.service.data.impl;

import com.example.xiao.logmanager.server.user.entity.po.SysAppPo;
import com.example.xiao.logmanager.server.user.dao.SysAppDao;
import com.example.xiao.logmanager.server.user.service.data.SysAppService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xiaozhiwei
 * @since 2024-02-24
 */
@Service
public class SysAppServiceImpl extends ServiceImpl<SysAppDao, SysAppPo> implements SysAppService {
    @Override
    public SysAppPo getByAppName(String appName) {
        return this.lambdaQuery().eq(SysAppPo::getAppName, appName)
                .one();
    }
}
