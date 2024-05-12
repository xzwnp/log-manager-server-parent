package com.example.xiao.logmanager.server.messaging.service.impl;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.example.xiao.logmanager.server.common.enums.ResultCode;
import com.example.xiao.logmanager.server.common.exception.BizException;
import com.example.xiao.logmanager.server.messaging.service.ShortMessageService;
import com.example.xiao.logmanager.server.messaging.util.AliyunMsmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * com.example.xiao.logmanager.server.messaging.service
 *
 * @author xzwnp
 * 2024/4/18
 * 11:22
 */
@Service
@Slf4j
public class ShortMessageServiceImpl implements ShortMessageService {
	@Override
	public void send(String to, String subject, String content) {
		sendBatch(List.of(to), subject, content);
	}

	@Override
	public void sendBatch(List<String> toUsers, String subject, String content) {
		Client client = null;
		String phoneNumbers = String.join(",", toUsers);
		try {
			client = AliyunMsmUtil.createClient();
			SendSmsRequest sendSmsRequest = new SendSmsRequest()
				.setSignName(subject)
				.setTemplateCode(subject)
				.setPhoneNumbers(phoneNumbers)
				.setTemplateParam(content);
			// 复制代码运行请自行打印 API 的返回值
			SendSmsResponse sendSmsResponse = client.sendSms(sendSmsRequest);
			if (!"OK".equals(sendSmsResponse.body.code)) {
				log.error(sendSmsResponse.body.message);
				throw new BizException(ResultCode.ERROR, "短信发送失败!");
			}
		} catch (Exception e) {
			log.error("短信服务初始化失败", e);
			throw new BizException(ResultCode.ERROR, "短信服务初始化失败!");
		}
	}
}
