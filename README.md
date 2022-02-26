# ad-springcloud

## 主要功能

广告投放系统是基于 springcloud 开发的项目，主要实现了广告的两个核心的系统。

1. **广告投放系统，面向广告主实现了广告投放系统。**重点是广告数据结构的设计，最终实现对外服务接口。
2. **广告检索系统，面向媒体方实现了广告的定向检索。**主要实现了构建索引和广告检索的功能，其中索引数据来自广告投放系统，预加载的部分称之为全量索引，同时检索系统（ ad-search ）会伪装为 MySQL 的 Slave 监听 Binlog，构建增量索引。检索过程依赖于全量和增量索引，实现快速高效的检索。
3. 区别于直接从数据库中检索数据，项目将检索数据存储在本地缓存中，引入倒排索引，构建全量索引+增量索引的组合，大幅减少了推广单元检索的时间。通过使用开源工具binlog-connector-java监听binlog构建增量索引，实现广告投放和检索服务解耦。通过Kafka重构广告检索服务，降低MySQL 负担。

## 创新点

1. 投放系统（ ad-sponsor ）引入**倒排索引**存储结构，构建全量索引+增量索引的组合，大幅减少推广单元检索时间。
2. 使用开源工具 Binlog-connector-java，根据MySQL Master/Slave协议， 检索系统 ( ad-search )伪装成 Slave 监听 Binlog，动态构建增量索引，实现广告投放和检索服务的解耦。
3. 检索系统（ad-search）多实例监听 Binlog 效率低，利用 Kafka 优化监听方式，通过 Navicat Monitor 测试，效率提升20%，性能，内存等等…。

## 项目架构图

<img src="https://gitee.com/oldwong77/cloudimg/raw/master/img/20220226163345.png" alt="image-20220226163345852" style="zoom:67%;" />

## 数据库表

<img src="https://gitee.com/oldwong77/cloudimg/raw/master/img/20220226110933.png" alt="image-20220226110926596" style="zoom: 50%;" />

<img src="https://gitee.com/oldwong77/cloudimg/raw/master/img/20220226164325.png" alt="image-20220226164325817" style="zoom:67%;" />

## 项目启动

1. 启动 kafka

   ```shell
   # 先启动zookeeper
   d:
   cd D:\env\kafka_2.12-2.1.0
   D:\env\kafka_2.12-2.1.0\bin\windows\zookeeper-server-start.bat config\zookeeper.properties
   
   # 再启动kafka
   d:
   cd D:\env\kafka_2.12-2.1.0
   bin\windows\kafka-server-start.bat config\server.properties
   ```

   说明：kafka 启动可能会报错，修改如下配置

   > 解决办法：修改kafka-run-class.bat中的179行，给%CLASSPATH%加上双引号即可，结果如下：
   >
   > set COMMAND=%JAVA% %KAFKA_HEAP_OPTS% %KAFKA_JVM_PERFORMANCE_OPTS% %KAFKA_JMX_OPTS% 
   > %KAFKA_LOG4J_OPTS% -cp "%CLASSPATH%" %KAFKA_OPTS% %*

2. 启动MySQL

   ```
   mysql -uroot -p123456
   ```

   说明：MySQL启动必须确保两个配置

   - 确保开启了 binlog 日志

   - 确保 binlog 日志的格式 ROW

     <img src="https://gitee.com/oldwong77/cloudimg/raw/master/img/20220226140049.png" alt="img" style="zoom:67%;" />

3. 启动 Eureka，所有的服务都需要向 Eureka 注册

4. 启动网关 zuul

5. 启动BinLog监听模块 ad-binlog-kafka

6. 启动广告检索模块 ad-search

7. 最终查看 Eureka 界面，查看服务是否成功

   默认访问路径 http://localhost:8000/

   ![img](https://gitee.com/oldwong77/cloudimg/raw/master/img/20220226141246.png)

## 功能测试

项目的一个核心功能：当我们对推广单元( ad_unit )进行增、删、改时，MySQL-binlog-connector 监听到 MySQL数据库 binlog 的变化，通过 kafka 发送修改信息，广告检索模块（ ad-search ) 订阅相关信息，并进行处理更新索引，实现广告检索服务功能。

首先我们查看广告推广单元 ad_unit 数据

![img](https://gitee.com/oldwong77/cloudimg/raw/master/img/20220226143308.png)

```java
select * from ad_unit;
update ad_unit set unit_status = 0 where id = 10;
```

更新一条数据

![img](https://gitee.com/oldwong77/cloudimg/raw/master/img/20220226143311.png)

我们查看ad-binlog-kafka，可以看出实现了向kafka的数据投递

![img](https://gitee.com/oldwong77/cloudimg/raw/master/img/20220226143304.png)

![img](https://gitee.com/oldwong77/cloudimg/raw/master/img/20220226143300.png)

此时我们查看ad-search

接受到了kafka投递过来的消息，进行索引的变化，可以看到变化前后索引的不同

![img](https://gitee.com/oldwong77/cloudimg/raw/master/img/20220226143255.png)

具体结果截取部分如下

> com.imooc.ad.consumer.BinlogConsumer     : kafka processMysqlRowData: {"fieldValueMap":[{"unit_status":"0","id":"10","position_type":"1","plan_id":"10"}],"level":"3","opType":"UPDATE","tableName":"ad_unit"}
>
> com.imooc.ad.index.adunit.AdUnitIndex    : before update: {10=AdUnitObject(unitId=10, unitStatus=1, positionType=1, planId=10, adPlanObject=AdPlanObject(planId=10, userId=15, planStatus=1, startDate=Wed Nov 28 00:00:00 CST 2018, endDate=Wed Nov 20 00:00:00 CST 2019)), 12=AdUnitObject(unitId=12, unitStatus=1, positionType=1, planId=10, adPlanObject=AdPlanObject(planId=10, userId=15, planStatus=1, startDate=Wed Nov 28 00:00:00 CST 2018, endDate=Wed Nov 20 00:00:00 CST 2019))}
>
> com.imooc.ad.index.adunit.AdUnitIndex    : after update: {10=AdUnitObject(unitId=10, unitStatus=0, positionType=1, planId=10, adPlanObject=AdPlanObject(planId=10, userId=15, planStatus=1, startDate=Wed Nov 28 00:00:00 CST 2018, endDate=Wed Nov 20 00:00:00 CST 2019)), 12=AdUnitObject(unitId=12, unitStatus=1, positionType=1, planId=10, adPlanObject=AdPlanObject(planId=10, userId=15, planStatus=1, startDate=Wed Nov 28 00:00:00 CST 2018, endDate=Wed Nov 20 00:00:00 CST 2019))}
>
>  c.n.d.s.r.aws.ConfigClusterResolver      : Resolving eureka endpoints via configuration

## 未来计划

- [ ] 广告投放系统的前端页面
- [ ] 提供用户注册登录等功能
- [ ] 引入ElasticSearch、Filebeat、Kibana 对打印日志做可视化，方便错误排查等
- [ ] 构建**人群画像**（当前是模拟用户数据），通过构建人群信息池来实现对广告信息的过滤
- [ ] 检索得到的用户创意，通过**智能推荐系统**（当前是对满足条件的多个创意进行随机选一个返回），返回用户最感兴趣的创意
- [ ] 提供更多维度的**条件限制**（当前仅为地域、兴趣、关键词）
