package com.example.xiao.logmanager.server.messaging.service;

import java.util.List;

/**
 * com.example.xiao.logmanager.server.messaging.service
 *
 * @author xzwnp
 * 2024/4/18
 * 11:11
 */
public interface ShortMessageService {
	/**
	 * 发送短信
	 *
	 * @param to      收件人手机号
	 * @param subject 主题
	 * @param content 内容
	 */
	void send(String to, String subject, String content);

	void sendBatch(List<String> toUsers, String subject, String content);
}
