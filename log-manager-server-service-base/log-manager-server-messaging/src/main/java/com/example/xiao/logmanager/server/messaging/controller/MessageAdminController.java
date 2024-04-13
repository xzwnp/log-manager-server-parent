package com.example.xiao.logmanager.server.messaging.controller;

import com.example.xiao.logmanager.server.common.entity.resp.R;
import com.example.xiao.logmanager.server.common.util.DateUtil;
import com.example.xiao.logmanager.server.common.util.UserThreadLocalUtil;
import com.example.xiao.logmanager.server.messaging.entity.po.StationMessagePo;
import com.example.xiao.logmanager.server.messaging.entity.resp.StationMessageResp;
import com.example.xiao.logmanager.server.messaging.service.StationMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/messaging")
@RequiredArgsConstructor
public class MessageAdminController {
    private final StationMessageService stationMessageService;

    @GetMapping("station-message/list")
    public R<List<StationMessageResp>> queryStationMessage() {
        List<StationMessagePo> list = stationMessageService.lambdaQuery()
                .eq(StationMessagePo::getUsername, UserThreadLocalUtil.getUserName())
                .orderByDesc(StationMessagePo::getCreatedTime).list();
        List<StationMessageResp> respList = list.stream().map(po ->
                        StationMessageResp.builder().id(po.getId()).type(po.getMessageType())
                                .title(po.getTitle()).description(po.getContent())
                                .dateTime(DateUtil.format(po.getCreatedTime(), "yyyy-MM-dd HH:mm:ss"))
                                .avatar("https://gw.alipayobjects.com/zos/rmsportal/ThXAXghbEsBCCSDihZxY.png")
                                .build())
                .toList();
        return R.success(respList);
    }
}
