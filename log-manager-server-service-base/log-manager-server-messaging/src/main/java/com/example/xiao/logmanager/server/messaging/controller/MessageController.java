package com.example.xiao.logmanager.server.messaging.controller;

import com.example.xiao.log.annotation.LogRecord;
import com.example.xiao.logmanager.api.enums.NotificationTypeEnum;
import com.example.xiao.logmanager.api.feign.SystemAdminFeignClient;
import com.example.xiao.logmanager.api.req.SendMessageRequest;
import com.example.xiao.logmanager.api.resp.UserInfoRpcResp;
import com.example.xiao.logmanager.server.common.entity.resp.R;
import com.example.xiao.logmanager.server.common.exception.BizException;
import com.example.xiao.logmanager.server.common.util.DateUtil;
import com.example.xiao.logmanager.server.messaging.entity.po.StationMessagePo;
import com.example.xiao.logmanager.server.messaging.entity.resp.StationMessageResp;
import com.example.xiao.logmanager.server.messaging.service.EmailService;
import com.example.xiao.logmanager.server.messaging.service.ShortMessageService;
import com.example.xiao.logmanager.server.messaging.service.StationMessageService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rpc/messaging")
@RequiredArgsConstructor
public class MessageController {
	private final SystemAdminFeignClient systemAdminFeignClient;
	private final EmailService emailService;
	private final StationMessageService stationMessageService;
	private final ShortMessageService shortMessageService;

	@PostMapping("send")
	@LogRecord(operate = "发送通知消息")
	public R<Void> sendMessage(@RequestBody SendMessageRequest request) {
		//查询目标用户的联系方式
		List<String> usernameList = request.getToUsers();
		R<List<UserInfoRpcResp>> userInfoResp = systemAdminFeignClient.getUserInfoBatch(usernameList);
		List<UserInfoRpcResp> userInfoList = userInfoResp.getData();
		for (NotificationTypeEnum notificationType : request.getNotificationTypes()) {
			switch (notificationType) {
				case EMAIL -> {
					List<String> emailList = userInfoList.stream().map(UserInfoRpcResp::getEmail).filter(StringUtils::isNotBlank).toList();
					emailService.sendSimpleMailBatch(emailList, request.getTitle(), request.getContent());
				}
				case SHORT_MESSAGE -> {
					shortMessageService.sendBatch(request.getToUsers(), request.getTitle(), request.getContent());
				}
				case STATION_MESSAGE -> {
					stationMessageService.saveStationMessage(request);
				}
			}
		}
		return R.success();
	}


}
