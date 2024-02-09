package com.example.xiao.logmanager.server.query.center.entity.req;

import com.example.xiao.logmanager.server.common.entity.req.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * com.example.xiao.logmanager.server.query.center.entity.req
 *
 * @author xzwnp
 * 2024/1/26
 * 13:59
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchAppLogReq extends PageRequest {
	private String appName;
	private String groupName;
	private String keyword;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
}
