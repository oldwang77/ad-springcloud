package com.imooc.ad.consumer;

import com.alibaba.fastjson.JSON;
import com.imooc.ad.dto.MySqlRowData;
import com.imooc.ad.sender.ISender;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 对kafka的topic监听，去消费kafka中的message，
 * 最终实现对广告索引数据的更新
 */
@Slf4j
@Component
public class BinlogConsumer {

    private final ISender sender;

    @Autowired
    public BinlogConsumer(ISender sender) {
        this.sender = sender;
    }

    @KafkaListener(topics = {"ad-search-mysql-data"}, groupId = "ad-search")
    public void processMysqlRowData(ConsumerRecord<?, ?> record) {

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        // 如果存在，不是空
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            MySqlRowData rowData = JSON.parseObject(
                    message.toString(),
                    MySqlRowData.class
            );
            log.info("kafka processMysqlRowData: {}", JSON.toJSONString(rowData));
            // kafka数据 ==> JVM 内存
            sender.sender(rowData);
        }
    }
}
