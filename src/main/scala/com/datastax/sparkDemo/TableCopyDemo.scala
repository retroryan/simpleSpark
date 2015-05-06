package com.datastax.sparkDemo

import com.datastax.spark.connector.cql.CassandraConnector
import com.datastax.spark.connector.demo.BasicReadWriteDemo._
import org.apache.spark.SparkContext

object TableCopyDemo extends DemoApp {

  def main(args: Array[String]): Unit = {

    val conf = getSparkConf()
    // Connect to the Spark cluster:
    val sc = new SparkContext(conf)

    CassandraConnector(conf).withSessionDo { session =>
      session.execute("CREATE KEYSPACE IF NOT EXISTS test WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': 1 }")
      session.execute("CREATE TABLE IF NOT EXISTS test.source (key INT PRIMARY KEY, data VARCHAR)")
      session.execute("CREATE TABLE IF NOT EXISTS test.destination (key INT PRIMARY KEY, data VARCHAR)")
      session.execute("TRUNCATE test.source")
      session.execute("TRUNCATE test.destination")
      session.execute("INSERT INTO test.source(key, data) VALUES (1, 'first row')")
      session.execute("INSERT INTO test.source(key, data) VALUES (2, 'second row')")
      session.execute("INSERT INTO test.source(key, data) VALUES (3, 'third row')")
    }

    import com.datastax.spark.connector._

    val src = sc.cassandraTable("test", "source")
    src.saveToCassandra("test", "destination")

    val dest = sc.cassandraTable("test", "destination")
    dest.collect().foreach(row => log.info(s"$row"))

    // Assert the rows were copied from test.source to test.destination table:
    assert(dest.collect().length == 3)

    log.info(s"Work completed, stopping the Spark context.")
    sc.stop()
  }
}
