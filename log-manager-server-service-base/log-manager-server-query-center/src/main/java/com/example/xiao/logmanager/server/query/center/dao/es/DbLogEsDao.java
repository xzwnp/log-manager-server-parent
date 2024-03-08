package com.example.xiao.logmanager.server.query.center.dao.es;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Highlight;
import co.elastic.clients.elasticsearch.core.search.HighlightField;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.util.ObjectBuilder;
import com.example.xiao.logmanager.server.common.entity.resp.PageDto;
import com.example.xiao.logmanager.server.query.center.entity.condition.SearchDbLogCondition;
import com.example.xiao.logmanager.server.query.center.entity.document.DbLogEsDocument;
import com.example.xiao.logmanager.server.query.center.entity.dto.DbLogKeywordGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
@Slf4j
public class DbLogEsDao {
    private static final String LOG_TYPE = "dblog";

    private final ElasticsearchClient esClient;


    public PageDto<DbLogEsDocument> search(SearchDbLogCondition condition) {
        PageDto<DbLogEsDocument> page = new PageDto<>(condition.getCurrent(), condition.getSize());
        Function<BoolQuery.Builder, ObjectBuilder<BoolQuery>> boolQuery = b -> {
            for (DbLogKeywordGroup keywordGroup : condition.getKeywordGroups()) {
                String fieldName;
                if (Boolean.TRUE.equals(keywordGroup.getMatchNewColumn())) {
                    fieldName = "new_columns." + keywordGroup.getColumnName();
                } else {
                    fieldName = "old_columns." + keywordGroup.getColumnName();
                }
                b.must(m -> m.matchPhrase(m2 -> m2.field(fieldName).query(keywordGroup.getColumnValue())));
            }
            //根据时间进行查找
            JsonData startTimeJson = JsonData.of(condition.getStartTime().toInstant(ZoneOffset.of("+8")).toEpochMilli());
            JsonData endTimeJson = JsonData.of(condition.getEndTime().toInstant(ZoneOffset.of("+8")).toEpochMilli());

            b.filter(f -> f.range(r -> r.field("@timestamp").gte(startTimeJson).lte(endTimeJson)));
            //根据日志级别查找
            if (condition.getChangeType() != null) {
                b.filter(f -> f.term(t -> t.field("change_type.keyword").value(condition.getChangeType().getName())));
            }
            //数据库名,表名
            if (StringUtils.isNotBlank(condition.getDatabase())) {
                b.filter(f -> f.term(t -> t.field("database.keyword").value(condition.getDatabase())));
            }
            if (StringUtils.isNotBlank(condition.getTable())) {
                b.filter(f -> f.term(t -> t.field("table.keyword").value(condition.getDatabase())));
            }
            return b;
        };

        SearchRequest.Builder searchRequestBuilder = new SearchRequest.Builder()
                .index(condition.getIndices())
                .from((int) condition.getOffset())
                .size((int) condition.getSize())
                .query(q -> q.bool(boolQuery));
        //设置排序
        SortOptions timeSort = new SortOptions.Builder().field(f -> f.field("@timestamp").order(condition.isTimeDesc() ? SortOrder.Desc : SortOrder.Asc)).build();
        searchRequestBuilder.sort(timeSort);
        try {
            SearchResponse<DbLogEsDocument> searchResponse = esClient.search(searchRequestBuilder.build(), DbLogEsDocument.class);
            HitsMetadata<DbLogEsDocument> hits = searchResponse.hits();
            long total = hits.total().value();
            page.setTotal(total);

            List<DbLogEsDocument> logs = searchResponse.hits().hits().stream().map(Hit::source).toList();
            page.setRecords(logs);
            log.info("总记录数: {}", total);
        } catch (
                IOException e) {
            log.error("DbLogEsDao search fail", e);
            throw new RuntimeException(e);
        }
        return page;
    }

    public String getIndexName(String appName, String group, LocalDate date) {
        String dateString = date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        return String.join("_", LOG_TYPE, appName, group, dateString);
    }
}
