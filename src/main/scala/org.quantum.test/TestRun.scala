package org.quantum.test

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext

object TestRun {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("test").setMaster("local")
    val sc = new SparkContext(conf)
    sc.setLogLevel("WARN")
    val sqlContext = new SQLContext(sc)

    /**
      * id      age
      * 1       30
      * 2       29
      * 3       21
      */
    case class Person(id: Int, age: Int)
    val idAgeRDDPerson = sc.parallelize(Array(Person(1, 30), Person(2, 29), Person(3, 21)))

    // 优点1
    // idAge.filter(_.age > "") // 编译时报错, int不能跟String比

    // 优点2
    idAgeRDDPerson.filter(_.age > 25) // 直接操作一个个的person对象
  }
}
