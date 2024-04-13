package com.example.xiao.logmanager.server.messaging.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.xiao.logmanager.api.req.SendMessageRequest;
import com.example.xiao.logmanager.server.messaging.dao.StationMessageDao;
import com.example.xiao.logmanager.server.messaging.entity.po.StationMessagePo;

public interface StationMessageService extends IService<StationMessagePo> {
    void saveStationMessage(SendMessageRequest request);
}
