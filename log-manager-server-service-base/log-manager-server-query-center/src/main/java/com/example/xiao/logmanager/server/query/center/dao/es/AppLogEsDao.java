package com.example.xiao.logmanager.server.query.center.dao.es;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.*;
import co.elastic.clients.elasticsearch.indices.GetIndexRequest;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.util.ObjectBuilder;
import com.example.xiao.logmanager.server.common.entity.resp.PageDto;
import com.example.xiao.logmanager.server.query.center.entity.condition.SearchAppLogCondition;
import com.example.xiao.logmanager.server.query.center.entity.document.AppLogEsDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;

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

    public PageDto<AppLogEsDocument> search(SearchAppLogCondition condition) {
        PageDto<AppLogEsDocument> page = new PageDto<>(condition.getCurrent(), condition.getSize());
        Function<BoolQuery.Builder, ObjectBuilder<BoolQuery>> boolQuery = b -> {
            if (StringUtils.isNotBlank(condition.getKeyword())) {
                //根据关键词进行检索
                if (condition.isTokenizeKeyword()) {
//                    String[] phrases = condition.getKeyword().split(",");
//                    b.must(m -> {
//                        for (String phrase : phrases) {
//                            m.matchPhrase(m2 -> m2.field("message").query(phrase));
//                        }
//                        return m;
//                    });
                    b.must(m -> m.match(m2 -> m2.field("message").query(condition.getKeyword())));
                } else {
                    b.must(m -> m.matchPhrase(m2 -> m2.field("message").query(condition.getKeyword())));
                }
            }
            if (StringUtils.isNotBlank(condition.getNegativeKeyword())) {
                //过滤包含排除关键词的文档
                b.mustNot(m -> m.matchPhrase(m2 -> m2.field("message").query(condition.getNegativeKeyword())));
            }
            //根据时间进行查找
            condition.setStartTime(condition.getStartTime().minusHours(8)); //解释:ES上存储的是UTC时间
            condition.setEndTime(condition.getEndTime().minusHours(8));
            b.filter(f -> f.range(r -> r.field("@timestamp").gte(JsonData.fromJson("\"" + condition.getStartTime() + "\"")).lte(JsonData.fromJson(("\"" + condition.getEndTime() + "\"")))));
            //根据日志级别查找
            if (condition.getLevel() != null) {
                b.filter(f -> f.term(t -> t.field("level.keyword").value(condition.getLevel().getName())));
            }
            //根据traceId进行查找
            if (StringUtils.isNotBlank(condition.getTraceId())) {
                b.filter(f -> f.term(t -> t.field("trace_id.keyword").value(condition.getTraceId())));
            }
            return b;
        };

        Function<Highlight.Builder, ObjectBuilder<Highlight>> highlight = h -> h
                .preTags("<mark>").postTags("</mark>") //指定高亮标签
                .numberOfFragments(0) //关闭分段
                .fields("message", HighlightField.of(hf -> hf));
        SearchRequest.Builder searchRequestBuilder = new SearchRequest.Builder()
                .index(condition.getIndices())
                .from((int) condition.getOffset())
                .size((int) condition.getSize())
                .query(q -> q.bool(boolQuery))
                .highlight(highlight);
        //设置排序
        SortOptions scoreSort = new SortOptions.Builder().score(score -> score.order(SortOrder.Desc)).build();
        SortOptions timeSort = new SortOptions.Builder().field(f -> f.field("@timestamp").order(condition.isTimeDesc() ? SortOrder.Desc : SortOrder.Asc)).build();
//        if (condition.isTokenizeKeyword()) {
//            //分词匹配场景下,搜索结果先根据分数后根据时间进行倒序排序
//            searchRequestBuilder.sort(scoreSort, timeSort);
//        } else {
//            //精确匹配场景下,搜索结果根据时间倒序排序
//            searchRequestBuilder.sort(timeSort);
//        }
        searchRequestBuilder.sort(timeSort);
        try {
            SearchResponse<AppLogEsDocument> searchResponse = esClient.search(searchRequestBuilder.build(), AppLogEsDocument.class);
            HitsMetadata<AppLogEsDocument> hits = searchResponse.hits();
            long total = hits.total().value();
            page.setTotal(total);

            //设置高亮
            for (Hit<AppLogEsDocument> hit : searchResponse.hits().hits()) {
                assert hit.source() != null;
                //有关键词才有高亮
                if (StringUtils.isNotBlank(condition.getKeyword())) {
                    hit.source().setMessageHighlight(String.join("", hit.highlight().get("message")));
                } else {
                    hit.source().setMessageHighlight(hit.source().getMessage());
                }
            }

            List<AppLogEsDocument> logs = searchResponse.hits().hits().stream().map(Hit::source).toList();
            page.setRecords(logs);
            log.info("总记录数: {}", total);
        } catch (IOException e) {
            log.error("AppLogEsDao search fail", e);
            throw new RuntimeException(e);
        }
        return page;
    }


}
