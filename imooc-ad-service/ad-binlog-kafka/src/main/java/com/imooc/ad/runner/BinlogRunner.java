package com.imooc.ad.runner;

import com.imooc.ad.mysql.BinlogClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * CommandLineRunner
 * 让springboot程序启动的时候，主动的运行
 */
@Slf4j
@Component
public class BinlogRunner implements CommandLineRunner {

    private final BinlogClient client;

    @Autowired
    public BinlogRunner(BinlogClient client) {
        this.client = client;
    }

    // String…是java5新加入的功能，表示的是一个可变长度的参数列表
    @Override
    public void run(String... strings) throws Exception {
        log.info("Coming in BinlogRunner...");
        client.connect();
    }
}
