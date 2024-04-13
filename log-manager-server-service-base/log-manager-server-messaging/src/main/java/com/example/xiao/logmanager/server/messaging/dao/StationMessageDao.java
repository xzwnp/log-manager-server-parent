package com.example.xiao.logmanager.server.messaging.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.xiao.logmanager.server.messaging.entity.po.StationMessagePo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StationMessageDao extends BaseMapper<StationMessagePo> {
}
