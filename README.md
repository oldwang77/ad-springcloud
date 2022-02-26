# ad-springcloud
## 项目说明



## 项目架构图

## 数据库表

<img src="https://gitee.com/oldwong77/cloudimg/raw/master/img/20220226110933.png" alt="image-20220226110926596" style="zoom: 50%;" />



## 项目启动

1. 启动kafka

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

   说明：kafka启动可能会报错，修改如下配置

   > 解决办法：修改kafka-run-class.bat中的179行，给%CLASSPATH%加上双引号即可，结果如下：
   >
   > set COMMAND=%JAVA% %KAFKA_HEAP_OPTS% %KAFKA_JVM_PERFORMANCE_OPTS% %KAFKA_JMX_OPTS% 
   > %KAFKA_LOG4J_OPTS% -cp "%CLASSPATH%" %KAFKA_OPTS% %*

2. 启动MySQL

   ```
   mysql -uroot -p123456
   ```

   说明：MySQL启动必须确保两个配置

   - 确保开启了binlog日志

   - 确保binlog日志的格式ROW

     <img src="https://gitee.com/oldwong77/cloudimg/raw/master/img/20220226140049.png" alt="img" style="zoom:67%;" />

3. 启动Eureka，所有的服务都需要向Eureka注册

4. 启动网关zuul

5. 启动BinLog监听模块ad-binlog-kafka

6. 启动广告检索模块ad-search

7. 最终查看Eureka界面，查看服务是否成功

   

## 功能测试

## 未来计划



## 开源协议

[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0.html)

