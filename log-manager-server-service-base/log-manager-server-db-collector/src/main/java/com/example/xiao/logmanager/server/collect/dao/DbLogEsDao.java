package com.example.xiao.logmanager.server.collect.dao;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.example.xiao.logmanager.server.collect.entity.document.DbLogEsDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
@Slf4j
public class DbLogEsDao {
    private static final String LOG_TYPE = "dblog";

    private final ElasticsearchClient esClient;

    public void save(DbLogEsDocument dbLog) {
        String indexName = getIndexName(dbLog.getAppName(), dbLog.getGroup(), dbLog.getTime().toLocalDate());
        IndexRequest<DbLogEsDocument> indexRequest = IndexRequest.of(i -> i.index(indexName).document(dbLog));
        try {
            IndexResponse indexResponse = esClient.index(indexRequest);
        } catch (IOException e) {
            log.error("save document[DbLogEsDocument] fail", e);
        }
    }

    public String getIndexName(String appName, String group, LocalDate date) {
        String dateString = date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        return String.join("_", LOG_TYPE, group, appName, dateString);
    }
}
