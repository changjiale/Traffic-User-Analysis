# Traffic-User-Analysis
一个基于上网流量的用户行为分析系统



系统分为2部分  

## 1电信流量分析。

根据现有的电信流量日志记录 (2015.6.15) 1w条 

从 77个存储业务数据中挑选 16个目标数据做具体的处理

| **业务字段**  | **字段类型** | **字段说明**   |
| ------------- | ------------ | -------------- |
| reportTime    | String       | 日志生成的时间 |
| cellid        | String       | 小区id         |
| appType       | **I**nt      | 应用大类       |
| appSubtype    | **I**nt      | 应用子类       |
| userIP        | String       | 用户id         |
| userPort      | **I**nt      | 用户端口       |
| appServerIP   | String       | 服务ip         |
| appServerPort | **I**nt      | 服务端口       |
| host          | String       | 域名           |
| attempts      | **I**nt      | 尝试次数       |
| accepts       | int          | 接收次数       |
| trafficUL     | long         | 上行流量       |
| trafficDL     | long         | 下行流量       |
| retranUL      | long         | 重传上行流量   |
| retranDL      | long         | 重传下行流量   |
| transDelay    | long         | 传输延迟       |

通过mapreduce处理数据    将数据存储在hdfs中。 通过sqoop导出到mysql数据库中 。

将数据按照不同的约束条件二次清洗, 用过echats生成图表.

## 2用户上网流量行为分析。

在电信流量分析形成的web网站的基础上   通过js埋点 ，获取用户的基本信息和访问网页的行为信息（访问量，访客数，会话数，跳出率，访问时长，访问深度等信息）

通过flume监听日志变化，写入hdfs中， 通过hive处理数据，导出到mysql中 二次形成用户上网流量行为的图标

