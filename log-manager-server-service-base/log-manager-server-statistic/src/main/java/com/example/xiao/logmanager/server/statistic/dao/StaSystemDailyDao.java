package com.example.xiao.logmanager.server.statistic.dao;

import com.example.xiao.logmanager.server.statistic.entity.po.StaSystemDailyPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 每日系统数据统计 Mapper 接口
 * </p>
 *
 * @author xiaozhiwei
 * @since 2024-04-20
 */
@Mapper
public interface StaSystemDailyDao extends BaseMapper<StaSystemDailyPo> {

}
