package com.example.xiao.logmanager.server.common.entity.req;

import lombok.Data;

/**
 * com.example.xiao.logmanager.server.common.entity.req
 *
 * @author xzwnp
 * 2024/1/26
 * 14:01
 */
@Data
public class PageRequest {
	protected Long current;
	protected Long size;
}
