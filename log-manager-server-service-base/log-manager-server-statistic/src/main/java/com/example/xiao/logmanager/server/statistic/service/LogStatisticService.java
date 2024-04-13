package com.example.xiao.logmanager.server.statistic.service;

import cn.hutool.core.collection.CollectionUtil;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.util.ObjectBuilder;
import com.example.xiao.logmanager.server.common.constant.SystemConstants;
import com.example.xiao.logmanager.server.common.exception.BizException;
import com.example.xiao.logmanager.server.common.util.DateUtil;
import com.example.xiao.logmanager.server.statistic.entity.req.OperateStatisticReq;
import com.example.xiao.logmanager.server.statistic.entity.resp.OperateStatisticResp;
import com.example.xiao.logmanager.server.statistic.util.EsClientHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LogStatisticService {
    public List<String> listOperates(String appName, String group) {
        String index = String.join("_", SystemConstants.OPERATE_LOG, group, appName, "*");

        String operateAggName = "operateAgg";
        SearchRequest searchRequest = new SearchRequest.Builder()
                .index(index)
                .size(0)
                .aggregations(operateAggName,
                        a -> a.terms(t -> t.field("operate.keyword").size(1000))
                )
                .build();
        SearchResponse<Object> searchResponse = EsClientHelper.executeWithRetry(esClient -> esClient.search(searchRequest, Object.class));
        Aggregate operateAgg = searchResponse.aggregations().get(operateAggName);
        Buckets<StringTermsBucket> buckets = operateAgg.sterms().buckets();
        return buckets.array().stream().map(StringTermsBucket::key).map(FieldValue::stringValue).collect(Collectors.toList());
    }

    public List<OperateStatisticResp> makeOperateStatistic(OperateStatisticReq req) {
        return switch (req.getDimension()) {
            case OPERATE_NAME -> makeOperateNameStatistic(req);
            case OPERATE_COST -> makeOperateCostStatistic(req);
            case OPERATE_TIME -> makeOperateTimeStatistic(req);
            default -> throw new BizException("不支持的统计维度");
        };
    }

    private List<OperateStatisticResp> makeOperateNameStatistic(OperateStatisticReq req) {
        String index = String.join("_", SystemConstants.OPERATE_LOG, req.getGroup(), req.getAppName(), "*");
        String operateAggName = "operateAgg";
        LocalDateTime startTime = req.getStartTime().minusHours(8); //解释:ES上存储的是UTC时间
        LocalDateTime endTime = req.getEndTime().minusHours(8);

        Function<Query.Builder, ObjectBuilder<Query>> boolQuery = q -> q.bool(b -> {
                    //根据操作名称过滤
                    if (CollectionUtil.isNotEmpty(req.getOperates())) {
                        List<FieldValue> fieldValues = req.getOperates().stream().map(FieldValue::of).toList();
                        b.filter(f -> f.terms(t -> t.field("operate").terms(ts -> ts.value(fieldValues))));
                    }
                    //根据时间过滤
                    b.filter(f -> f.range(r -> r.field("@timestamp").gte(JsonData.fromJson("\"" + startTime + "\"")).lte(JsonData.fromJson(("\"" + endTime + "\"")))));
                    return b;
                }
        );

        SearchRequest.Builder searchRequestBuilder = new SearchRequest.Builder()
                .index(index)
                .size(0)
                .query(boolQuery)
                .aggregations(operateAggName,
                        a -> a.terms(t -> t.field("operate.keyword").size(1000))
                );

        SearchRequest searchRequest = searchRequestBuilder.build();
        SearchResponse<Object> searchResponse = EsClientHelper.executeWithRetry(esClient -> esClient.search(searchRequest, Object.class));

        Aggregate operateAgg = searchResponse.aggregations().get(operateAggName);
        Buckets<StringTermsBucket> buckets = operateAgg.sterms().buckets();
        List<String> operateNames = buckets.array().stream().map(StringTermsBucket::key).map(FieldValue::stringValue).collect(Collectors.toList());
        List<Long> operateCounts = buckets.array().stream().map(StringTermsBucket::docCount).collect(Collectors.toList());

        OperateStatisticResp resp = OperateStatisticResp.builder()
                .legend("操作次数")
                .xAxis(operateNames)
                .counts(operateCounts)
                .build();
        return List.of(resp);
    }

    private List<OperateStatisticResp> makeOperateCostStatistic(OperateStatisticReq req) {
        String index = String.join("_", SystemConstants.OPERATE_LOG, req.getGroup(), req.getAppName(), "*");
        String operateAggName = "operateAgg";
        String operateTimeAggName = "operateTimeAgg";
        LocalDateTime startTime = req.getStartTime().minusHours(8); //解释:ES上存储的是UTC时间
        LocalDateTime endTime = req.getEndTime().minusHours(8);

        Function<Query.Builder, ObjectBuilder<Query>> boolQuery = q -> q.bool(b -> {
                    //根据操作名称过滤
                    if (CollectionUtil.isNotEmpty(req.getOperates())) {
                        List<FieldValue> fieldValues = req.getOperates().stream().map(FieldValue::of).toList();
                        b.filter(f -> f.terms(t -> t.field("operate").terms(ts -> ts.value(fieldValues))));
                    }
                    //根据时间过滤
                    b.filter(f -> f.range(r -> r.field("@timestamp").gte(JsonData.fromJson("\"" + startTime + "\"")).lte(JsonData.fromJson(("\"" + endTime + "\"")))));
                    return b;
                }
        );

        //先根据操作名称分组,然后根据操作耗时分组
        List<AggregationRange> ranges = Arrays.asList(
                AggregationRange.of(r -> r.to("10")),
                AggregationRange.of(r -> r.from("10").to("100")),
                AggregationRange.of(r -> r.from("100").to("200")),
                AggregationRange.of(r -> r.from("500").to("1000")),
                AggregationRange.of(r -> r.from("1000").to("5000")),
                AggregationRange.of(r -> r.from("5000"))
        );
        SearchRequest.Builder searchRequestBuilder = new SearchRequest.Builder()
                .index(index)
                .size(0)
                .query(boolQuery)
                .aggregations(operateAggName,
                        a -> a.terms(t -> t.field("operate.keyword").size(1000))
                                .aggregations(operateTimeAggName,
                                        a2 -> a2.range(d -> d.field("time_cost").ranges(ranges))
                                )
                );
        SearchRequest searchRequest = searchRequestBuilder.build();

        SearchResponse<Object> searchResponse = EsClientHelper.executeWithRetry(esClient -> esClient.search(searchRequest, Object.class));

        Aggregate operateAgg = searchResponse.aggregations().get(operateAggName);
        Buckets<StringTermsBucket> buckets = operateAgg.sterms().buckets();
        List<OperateStatisticResp> respList = new ArrayList<>();
        for (StringTermsBucket bucket : buckets.array()) {
            Aggregate timeAgg = bucket.aggregations().get(operateTimeAggName);
            List<RangeBucket> timeBuckets = timeAgg.range().buckets().array();
            List<String> xAxis = Arrays.asList("<10ms", "10-100ms", "100-500ms", "500-1000ms", "1000-5000ms", ">5000ms");
            List<Long> counts = timeBuckets.stream().map(RangeBucket::docCount).toList();
            OperateStatisticResp resp = OperateStatisticResp.builder()
                    .legend(bucket.key().stringValue())
                    .xAxis(xAxis)
                    .counts(counts)
                    .build();
            respList.add(resp);
        }
        return respList;
    }

    private List<OperateStatisticResp> makeOperateTimeStatistic(OperateStatisticReq req) {
        String index = String.join("_", SystemConstants.OPERATE_LOG, req.getGroup(), req.getAppName(), "*");
        String operateAggName = "operateAgg";
        String operateTimeAggName = "operateTimeAgg";
        LocalDateTime startTime = req.getStartTime().minusHours(8); //解释:ES上存储的是UTC时间
        LocalDateTime endTime = req.getEndTime().minusHours(8);

        Function<Query.Builder, ObjectBuilder<Query>> boolQuery = q -> q.bool(b -> {
                    //根据操作名称过滤
                    if (CollectionUtil.isNotEmpty(req.getOperates())) {
                        List<FieldValue> fieldValues = req.getOperates().stream().map(FieldValue::of).toList();
                        b.filter(f -> f.terms(t -> t.field("operate").terms(ts -> ts.value(fieldValues))));
                    }
                    //根据时间过滤
                    b.filter(f -> f.range(r -> r.field("@timestamp").gte(JsonData.fromJson("\"" + startTime + "\"")).lte(JsonData.fromJson(("\"" + endTime + "\"")))));
                    return b;
                }
        );

        SearchRequest.Builder searchRequestBuilder = new SearchRequest.Builder()
                .index(index)
                .size(0)
                .query(boolQuery)
                .aggregations(operateAggName,
                        a -> a.terms(t -> t.field("operate.keyword").size(1000)).aggregations(
                                operateTimeAggName, a2 -> a2.autoDateHistogram(d -> d.field("@timestamp"))
                        )
                );

        SearchRequest searchRequest = searchRequestBuilder.build();
        SearchResponse<Object> searchResponse = EsClientHelper.executeWithRetry(esClient -> esClient.search(searchRequest, Object.class));

        Aggregate operateAgg = searchResponse.aggregations().get(operateAggName);
        Buckets<StringTermsBucket> buckets = operateAgg.sterms().buckets();
        List<OperateStatisticResp> respList = new ArrayList<>();
        for (StringTermsBucket bucket : buckets.array()) {
            Aggregate timeAgg = bucket.aggregations().get(operateTimeAggName);
            List<DateHistogramBucket> timeBuckets = timeAgg.autoDateHistogram().buckets().array();

            List<String> xAxis = timeBuckets.stream().map(DateHistogramBucket::key).map(timestamp -> DateUtil.format(timestamp, "MM-dd HH:mm:ss")).toList();
            List<Long> counts = timeBuckets.stream().map(DateHistogramBucket::docCount).toList();
            OperateStatisticResp resp = OperateStatisticResp.builder()
                    .legend(bucket.key().stringValue())
                    .xAxis(xAxis)
                    .counts(counts)
                    .build();
            respList.add(resp);
        }
        return respList;
    }
}
