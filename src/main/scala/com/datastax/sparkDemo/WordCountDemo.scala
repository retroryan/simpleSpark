package com.datastax.spark.connector.demo

import com.datastax.spark.connector.demo.BasicReadWriteDemo._
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import com.datastax.spark.connector.cql.CassandraConnector
import com.datastax.spark.connector._

// copy the file words in the root directory into the /tmp directory on BOTH the client and server.
// Then run:
// sbt 'runMain com.datastax.spark.connector.demo.WordCountDemo'

object WordCountDemo extends DemoApp {

  def main(args: Array[String]): Unit = {

    val conf = getSparkConf()
    // Connect to the Spark cluster:
    val sc = new SparkContext(conf)

    CassandraConnector(conf).withSessionDo { session =>
      session.execute(s"CREATE KEYSPACE IF NOT EXISTS demo WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': 1 }")
      session.execute(s"CREATE TABLE IF NOT EXISTS demo.wordcount (word TEXT PRIMARY KEY, count COUNTER)")
      session.execute(s"TRUNCATE demo.wordcount")
    }

    sc.textFile(words)
      .flatMap(_.split("\\s+"))
      .map(word => (word.toLowerCase, 1))
      .reduceByKey(_ + _)
      .saveToCassandra("demo", "wordcount")

    // print out the data saved from Spark to Cassandra
    sc.cassandraTable("demo", "wordcount").collect.foreach(println)
    sc.stop()
  }
}
