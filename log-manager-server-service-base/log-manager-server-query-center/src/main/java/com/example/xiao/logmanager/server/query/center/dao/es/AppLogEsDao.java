package com.example.xiao.logmanager.server.query.center.dao.es;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.indices.GetIndexRequest;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import com.example.xiao.logmanager.server.query.center.entity.condition.SearchIndexCondition;
import com.example.xiao.logmanager.server.query.center.entity.document.AppLogEsDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * com.example.xiao.logmanager.server.query.center.dao.es
 *
 * @author xzwnp
 * 2024/1/25
 * 21:55
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AppLogEsDao {
	private final ElasticsearchClient esClient;

	public List<String> listIndices(String indexPattern) {

		GetIndexRequest getIndexRequest = new GetIndexRequest.Builder().index(indexPattern).build();

		GetIndexResponse getIndexResponse = null;
		try {
			getIndexResponse = esClient.indices().get(getIndexRequest);
		} catch (IOException e) {
			log.error("call es fail", e);
			throw new RuntimeException(e);
		}
		Set<String> indices = getIndexResponse.result().keySet();
		return new ArrayList<>(indices);
	}

	public List<String> search(SearchIndexCondition condition) {
		SearchRequest searchRequest = new SearchRequest.Builder()
			.index(condition.getIndices())
//			.query(QueryBuilders.matchAll().build()._toQuery())
			.query(q -> q.matchAll(b -> b))
			.from(condition.getOffset().intValue())
			.size(condition.getSize().intValue())
			.build();
		try {
			SearchResponse<AppLogEsDocument> response = esClient.search(searchRequest, AppLogEsDocument.class);
			HitsMetadata<AppLogEsDocument> hits = response.hits();
			for (Hit<AppLogEsDocument> hit : hits.hits()) {
				System.out.println(hit. ());

			}
			TotalHits total = hits.total();
			System.out.println("当前页数: " + pageNumber);
			System.out.println("每页大小: " + pageSize);
			System.out.println("总记录数: " + searchResponse.getHits().getTotalHits().value);
		} catch (IOException e) {
			log.error("AppLogEsDao search fail", e);
			throw new RuntimeException(e);
		}

		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);


	}


}
