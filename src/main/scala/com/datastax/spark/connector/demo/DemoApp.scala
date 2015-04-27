package com.datastax.spark.connector.demo

import com.datastax.bdp.spark.DseSparkConfHelper
import org.apache.spark.{Logging, SparkContext, SparkConf}

trait DemoApp extends Logging {

  val words = "/tmp/words"

  val DSE_HOST = "10.0.0.14"

  //Driver Host is the ip of the application running the spark application
  val DRIVER_HOST = "10.0.0.26"

  def getSparkConf() = {

    // Tell Spark the address of one Cassandra node:
    val sc = new SparkConf()
        .setAppName("techsupply")
        .set("spark.cassandra.connection.host",DSE_HOST)
        .set("spark.driver.host", DRIVER_HOST)
        .set("spark.eventLog.enabled", "true")
        .set("spark.eventLog.dir", "/Users/ryanknight/dse/logs/spark/eventLog")
        .setMaster(s"spark://${DSE_HOST}:7077")
        .setJars(Array("target/scala-2.10/simpleSpark-assembly-0.2.0.jar"))

    //val dseSC = DseSparkConfHelper.enrichSparkConf(sc)

    println(sc.toDebugString)

    sc
  }
}

object DemoApp {
  def apply(): DemoApp = new DemoApp {}
}
