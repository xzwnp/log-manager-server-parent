package com.example.xiao.logmanager.server.query.center.entity.condition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * com.example.xiao.logmanager.server.query.center.entity.cond
 *
 * @author xzwnp
 * 2024/1/26
 * 17:56
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchIndexCondition {
	private List<String> indices;
	private Long current;
	private Long size;
	private String keyword;

	public Long getOffset() {
		return (current - 1) * size;
	}
}
