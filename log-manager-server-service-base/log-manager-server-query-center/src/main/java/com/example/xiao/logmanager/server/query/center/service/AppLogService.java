package com.example.xiao.logmanager.server.query.center.service;

import com.example.xiao.logmanager.server.common.entity.resp.PageResponse;
import com.example.xiao.logmanager.server.query.center.dao.es.AppLogEsDao;
import com.example.xiao.logmanager.server.query.center.entity.condition.SearchIndexCondition;
import com.example.xiao.logmanager.server.query.center.entity.dto.AppLogIndexDto;
import com.example.xiao.logmanager.server.query.center.entity.req.SearchAppLogReq;
import com.example.xiao.logmanager.server.query.center.entity.resp.SearchAppLogResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * com.example.xiao.logmanager.server.query.center.service
 *
 * @author xzwnp
 * 2024/1/25
 * 22:10
 */
@Service
@RequiredArgsConstructor
public class AppLogService {
	private static final String LOG_TYPE = "applog";
	private final AppLogEsDao appLogEsDao;

	public List<AppLogIndexDto> listAppLogs() {
		List<String> indices = appLogEsDao.listIndices(LOG_TYPE + "*");
		Map<String, AppLogIndexDto> indexDtoMap = new HashMap<>();
		// 处理返回的索引信息
		for (String index : indices) {
			try {
				//%{log-type}_%{group}_%{app-name}_%{date}
				String[] split = index.split("_");
				String groupName = split[1];
				String appName = split[2];
				AppLogIndexDto indexDto = indexDtoMap.get(appName);
				if (indexDto == null) {
					indexDto = new AppLogIndexDto(appName, new HashSet<>());
					indexDtoMap.put(appName, indexDto);
				}
				indexDto.getGroupNames().add(groupName);
				System.out.println(appName);
			} catch (IndexOutOfBoundsException e) {
				//索引格式不合规,跳过....
			}

		}
		return new ArrayList<>(indexDtoMap.values());
	}

	public PageResponse<SearchAppLogResp> searchAppLogs(SearchAppLogReq req) {
		String index = String.join("_", LOG_TYPE, req.getGroupName(), req.getAppName(), "*");
		SearchIndexCondition condition = SearchIndexCondition.builder().indices(List.of(index)).keyword(req.getKeyword())
			.current(req.getCurrent()).size(req.getSize()).build();
		appLogEsDao.search(condition);
		return null;
	}
}
