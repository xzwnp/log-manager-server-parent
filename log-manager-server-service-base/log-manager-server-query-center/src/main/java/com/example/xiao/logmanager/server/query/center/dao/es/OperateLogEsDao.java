package com.example.xiao.logmanager.server.query.center.dao.es;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.HighlightField;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.elasticsearch.indices.GetIndexRequest;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.util.ObjectBuilder;
import com.example.xiao.logmanager.server.common.entity.resp.PageDto;
import com.example.xiao.logmanager.server.query.center.entity.condition.SearchOperateLogCondition;
import com.example.xiao.logmanager.server.query.center.entity.document.AppLogEsDocument;
import com.example.xiao.logmanager.server.query.center.entity.document.OperateLogEsDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
public class OperateLogEsDao {
    private final ElasticsearchClient esClient;

    public PageDto<OperateLogEsDocument> search(SearchOperateLogCondition condition) {
        PageDto<OperateLogEsDocument> page = new PageDto<>(condition.getCurrent(), condition.getSize());
        Function<BoolQuery.Builder, ObjectBuilder<BoolQuery>> boolQuery = b -> {
            if (StringUtils.isNotBlank(condition.getOperate())) {
                //根据操作名称检索
                b.must(m -> m.matchPhrase(m2 -> m2.field("operate").query(condition.getOperate())));
            }
            //根据时间进行查找
            condition.setStartTime(condition.getStartTime().minusHours(8)); //解释:ES上存储的是UTC时间
            condition.setEndTime(condition.getEndTime().minusHours(8));
            b.filter(f -> f.range(r -> r.field("@timestamp").gte(JsonData.fromJson("\"" + condition.getStartTime() + "\"")).lte(JsonData.fromJson(("\"" + condition.getEndTime() + "\"")))));
            //根据操作员id查找
            if (StringUtils.isNotBlank(condition.getOperatorId())) {
                b.filter(f -> f.term(t -> t.field("operator_id.keyword").value(condition.getOperatorId())));
            }
            //根据操作员名称查找
            if (StringUtils.isNotBlank(condition.getOperatorName())) {
                b.filter(f -> f.term(t -> t.field("operator_name.keyword").value(condition.getOperatorName())));
            }
            //根据traceId进行查找
            if (StringUtils.isNotBlank(condition.getTraceId())) {
                b.filter(f -> f.term(t -> t.field("trace_id.keyword").value(condition.getTraceId())));
            }
            return b;
        };

        SearchRequest.Builder searchRequestBuilder = new SearchRequest.Builder()
                .index(condition.getIndices())
                .from((int) condition.getOffset())
                .size((int) condition.getSize())
                .query(q -> q.bool(boolQuery));
        //设置排序
        SortOptions timeSort = new SortOptions.Builder().field(f -> f.field("@timestamp").order(condition.isTimeDesc()?SortOrder.Desc:SortOrder.Asc)).build();
        searchRequestBuilder.sort(timeSort);

        try {
            SearchResponse<OperateLogEsDocument> searchResponse = esClient.search(searchRequestBuilder.build(), OperateLogEsDocument.class);
            HitsMetadata<OperateLogEsDocument> hits = searchResponse.hits();
            long total = hits.total().value();
            page.setTotal(total);

            List<OperateLogEsDocument> logs = searchResponse.hits().hits().stream().map(Hit::source).toList();
            page.setRecords(logs);
            log.info("searchOperateLog 总记录数: {},搜索结果:{}", total, logs);
        } catch (
                IOException e) {
            log.error("OperateLog search fail", e);
            throw new RuntimeException(e);
        }
        return page;
    }


}
