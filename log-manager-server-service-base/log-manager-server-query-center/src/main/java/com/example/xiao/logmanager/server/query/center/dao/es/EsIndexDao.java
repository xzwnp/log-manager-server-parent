package com.example.xiao.logmanager.server.query.center.dao.es;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.GetIndexRequest;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class EsIndexDao {
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
}