package com.imooc.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * <h1>Binlog Kafka 引用启动程序</h1>
 */
@EnableEurekaClient
@SpringBootApplication
public class BinlogKafkaApplication {

    public static void main(String[] args) {

        SpringApplication.run(BinlogKafkaApplication.class, args);
    }
}
