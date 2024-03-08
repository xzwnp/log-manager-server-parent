package com.example.xiao.logmanager.server.collect.runner;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.example.xiao.logmanager.server.collect.config.DbCollectorProperties;
import com.example.xiao.logmanager.server.collect.dao.DbLogEsDao;
import com.example.xiao.logmanager.server.collect.entity.document.DbLogEsDocument;
import com.example.xiao.logmanager.server.collect.enums.RowChangeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 订阅canal服务端发送的数据库变更日志,获取数据变更详情
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CanalClientRunner implements CommandLineRunner, InitializingBean {
    private final DbCollectorProperties properties;

    private static final Map<String, Pair<String, String>> dbAppMap = new HashMap<>();

    private final DbLogEsDao dbLogEsDao;

    @Override
    public void run(String... args) throws Exception {
        log.info("Listening Canal Server");
// 创建链接
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress(properties.getHost(),
                properties.getPort()), properties.getDestination(), properties.getUsername(), properties.getPassword());
        try {
            connector.connect();
            connector.subscribe(".*\\..*");
            connector.rollback();
            while (true) {
                Message message = connector.getWithoutAck(properties.getMaxBatchSize()); // 获取指定数量的数据
                long batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    if (log.isDebugEnabled()) {
                        log.debug("no data from server, waiting......");
                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        log.warn("CanalClientRunner interrupted");
                    }
                    connector.ack(batchId); // 提交确认
                } else {
                    try {
                        handleEntry(message.getEntries());
                        connector.ack(batchId); // 提交确认
                    } catch (Exception e) {
                        connector.rollback(batchId); // 处理失败, 回滚数据
                        log.error("consume data from canal server fail", e);
                        throw new RuntimeException(e);
                    }
                }
            }
        } finally {
            connector.disconnect();
        }
    }

    private void handleEntry(List<CanalEntry.Entry> entries) {
        for (CanalEntry.Entry entry : entries) {
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                continue;
            }

            CanalEntry.RowChange rowChage = null;
            try {
                rowChage = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }

            CanalEntry.EventType eventType = rowChage.getEventType();
            String databaseName = entry.getHeader().getSchemaName();
            String tableName = entry.getHeader().getTableName();
            log.info("================> binlog of table[{}:{}],eventType : {}", databaseName, tableName, eventType);

            for (CanalEntry.RowData rowData : rowChage.getRowDatasList()) {
                //仅开发阶段,打印数据变更情况
                if (eventType == CanalEntry.EventType.DELETE) {
                    printColumn(rowData.getBeforeColumnsList());
                } else if (eventType == CanalEntry.EventType.INSERT) {
                    printColumn(rowData.getAfterColumnsList());
                } else {
                    log.info("-------> before");
                    printColumn(rowData.getBeforeColumnsList());
                    log.info("-------> after");
                    printColumn(rowData.getAfterColumnsList());
                }

                //保存到es
                Pair<String, String> appGroupPair = dbAppMap.get(databaseName);
                if (appGroupPair == null) {
                    log.warn("database [{}] does not have appName and group configured", databaseName);
                    continue;
                }
                Map<String, String> oldColumns = rowData.getBeforeColumnsList().stream().collect(Collectors.toMap(CanalEntry.Column::getName, CanalEntry.Column::getValue));
                Map<String, String> newColumns = rowData.getAfterColumnsList().stream().collect(Collectors.toMap(CanalEntry.Column::getName, CanalEntry.Column::getValue));
                Map<String, List<String>> diffColumns = rowData.getAfterColumnsList().stream().filter(CanalEntry.Column::getUpdated).collect(Collectors.toMap(CanalEntry.Column::getName, col -> Arrays.asList(oldColumns.get(col.getName()), col.getValue())));
                DbLogEsDocument dbLogEsDocument = DbLogEsDocument.builder()
                        .appName(appGroupPair.getLeft())
                        .group(appGroupPair.getRight())
                        .database(databaseName)
                        .table(tableName)
                        .time(LocalDateTime.now())
                        .changeType(RowChangeType.of(eventType.getNumber()))
                        .oldColumns(oldColumns)
                        .newColumns(newColumns)
                        .diffColumns(diffColumns)
                        .timestamp(new Date())
                        .version(1)
                        .build();
                dbLogEsDao.save(dbLogEsDocument);
            }


        }
    }

    private void printColumn(List<CanalEntry.Column> columns) {
        for (CanalEntry.Column column : columns) {
            log.info(column.getName() + " : " + column.getValue() + "    modified:" + column.getUpdated());
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        for (DbCollectorProperties.DataBaseConfig router : properties.getRouters()) {
            dbAppMap.put(router.getDatabase(), Pair.of(router.getAppName(), router.getGroup()));
        }
    }
}
