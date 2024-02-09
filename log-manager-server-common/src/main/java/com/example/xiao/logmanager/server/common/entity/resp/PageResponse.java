package com.example.xiao.logmanager.server.common.entity.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * com.example.xiao.logmanager.server.common.entity.resp
 *
 * @author xzwnp
 * 2024/1/26
 * 14:02
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {
	protected Long total;
	protected Long current;
	protected Long size;
	protected List<T> records;

	public PageResponse(Long current, Long size) {
		this.current = current;
		this.size = size;
	}
}