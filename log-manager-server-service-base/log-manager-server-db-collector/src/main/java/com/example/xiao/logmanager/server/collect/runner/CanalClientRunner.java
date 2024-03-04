package com.example.xiao.logmanager.server.collect.runner;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.example.xiao.logmanager.server.collect.config.DbCollectorProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 订阅canal服务端发送的数据库变更日志,获取数据变更详情
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CanalClientRunner implements CommandLineRunner {
    private final DbCollectorProperties properties;

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

    private static void handleEntry(List<CanalEntry.Entry> entries) {
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
            System.out.println(String.format("================> binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));

            for (CanalEntry.RowData rowData : rowChage.getRowDatasList()) {
                if (eventType == CanalEntry.EventType.DELETE) {
                    printColumn(rowData.getBeforeColumnsList());
                } else if (eventType == CanalEntry.EventType.INSERT) {
                    printColumn(rowData.getAfterColumnsList());
                } else {
                    System.out.println("-------> before");
                    printColumn(rowData.getBeforeColumnsList());
                    System.out.println("-------> after");
                    printColumn(rowData.getAfterColumnsList());
                }
            }
        }
    }

    private static void printColumn(List<CanalEntry.Column> columns) {
        for (CanalEntry.Column column : columns) {
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
        }
    }
}
