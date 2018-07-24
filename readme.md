### 必须指定master时
spark可以支持多种环境，但有时需要制定环境。

```$xslt
local 本地单线程
local[K] 本地多线程（指定K个内核）
local[*] 本地多线程（指定所有可用内核）
spark://HOST:PORT 连接到指定的 Spark standalone cluster master，需要指定端口。
mesos://HOST:PORT 连接到指定的 Mesos 集群，需要指定端口。
yarn-client客户端模式 连接到 YARN 集群。需要配置 HADOOP_CONF_DIR。
yarn-cluster集群模式 连接到 YARN 集群。需要配置 HADOOP_CONF_DIR。
```


### SparkSession和SparkContext
SparkSession是Spark 2.0引如的新概念。SparkSession为用户提供了统一的切入点，来让用户学习spark的各项功能。 

在spark的早期版本中，SparkContext是spark的主要切入点，由于RDD是主要的API，我们通过sparkcontext来创建和操作RDD。对于每个其他的API，我们需要使用不同的context。例如，对于Streming，我们需要使用StreamingContext；对于sql，使用sqlContext；对于hive，使用hiveContext。但是随着DataSet和DataFrame的API逐渐成为标准的API，就需要为他们建立接入点。所以在spark2.0中，引入SparkSession作为DataSet和DataFrame API的切入点，SparkSession封装了SparkConf、SparkContext和SQLContext。为了向后兼容，SQLContext和HiveContext也被保存下来。

- 创建SparkSession

我们可以使用builder模式来创建SparkSession。如果SparkContext已经存在，SparkSession就会重用它；如果不存在，Spark就会创建一个新的SparkContext。在每一个JVM中只能有一个SparkContext，但是在一个Spark程序中可以有多个SparkSession。

```java
SparkSession spark = SparkSession.builder()
    .master("yourmaster")
    .appName("UnderstandingSparkSession")
    .config("spark.some.config.option", "config-value")
    .getOrCreate();
```

当然你也可以通过SparkSession来创建SparkContext。

```java
JavaSparkContext sc = new JavaSparkContext(spark.sparkContext());
```

[Spark之SparkSession](https://blog.csdn.net/u012430664/article/details/58055457)

### spark 环境配置
```
export SPARK_HOME=/opt/spark/spark-2.3.0-bin-hadoop2.6
export PATH=$PATH:$SPARK_HOME/bin:$SPARK_HOME
```