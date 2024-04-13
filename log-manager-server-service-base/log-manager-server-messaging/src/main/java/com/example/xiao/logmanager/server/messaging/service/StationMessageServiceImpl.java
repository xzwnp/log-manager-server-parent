package com.example.xiao.logmanager.server.messaging.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.xiao.logmanager.api.req.SendMessageRequest;
import com.example.xiao.logmanager.server.messaging.dao.StationMessageDao;
import com.example.xiao.logmanager.server.messaging.entity.po.StationMessagePo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StationMessageServiceImpl extends ServiceImpl<StationMessageDao, StationMessagePo> implements StationMessageService {
    @Override
    @Transactional
    public void saveStationMessage(SendMessageRequest request) {
        List<StationMessagePo> stationMessagePoList = request.getToUsers().stream().map(username -> {
            StationMessagePo stationMessagePo;
            stationMessagePo = new StationMessagePo().setMessageType(1).setTitle(request.getTitle()).setContent(request.getContent()).setReadFlag(false)
                    .setUsername(username);
            return stationMessagePo;
        }).toList();
        this.saveBatch(stationMessagePoList);
    }
}
