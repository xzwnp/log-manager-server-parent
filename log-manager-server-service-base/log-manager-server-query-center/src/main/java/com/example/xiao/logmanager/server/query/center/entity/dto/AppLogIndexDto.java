package com.example.xiao.logmanager.server.query.center.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * com.example.xiao.logmanager.server.query.center.entity.dto
 *
 * @author xzwnp
 * 2024/1/26
 * 13:34
 */
@Data
@AllArgsConstructor
public class AppLogIndexDto {
	private String appName;
	private Set<String> groupNames;

	public AppLogIndexDto() {
		this.groupNames = new HashSet<>();
	}
}
