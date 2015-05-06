Simple Spark Project Example 
=============================


Example of a Simple Spark project that bundles into a single jar and runs the specified main.

Look at using Spark Submit (dse spark-submit submit --class) or the [Spark Job Server](https://github.com/spark-jobserver/spark-jobserver) instead.

How to Run
==========

* checkout source
* Change the IP to point to your client application IP (where this program runs) and server IP address in DemoApp.scala
* brew install sbt
* In the command prompt: 
* sbt assembly
*  ~/dse/bin/dse spark-submit --class com.datastax.sparkDemo.BasicReadWriteDemo ./target/scala-2.10/simpleSpark-assembly-0.2.0.jar

This exception is normal:  

15/05/06 12:01:56 INFO ConnectionManager: key already cancelled ? sun.nio.ch.SelectionKeyImpl@7f29c5dc
                           java.nio.channels.CancelledKeyException
                           	at org.apache.spark.network.ConnectionManager.run(ConnectionManager.scala:386)
                           	at org.apache.spark.network.ConnectionManager$$anon$4.run(ConnectionManager.scala:139)
                           	

* verify result in cqlsh:

dse/bin/cqlsh

cqlsh> use test;

cqlsh:test> select * from key_value;

*  ~/dse/bin/dse spark-submit --class com.datastax.sparkDemo.TableCopyDemo ./target/scala-2.10/simpleSpark-assembly-0.2.0.jar


* verify result in cqlsh:

cqlsh:test> select * from source;

cqlsh:test> select * from destination;


Kafka Samples
==========

Start Kafka, create the topics and test:

bin/zookeeper-server-start.sh config/zookeeper.properties

bin/kafka-server-start.sh config/server.properties

bin/kafka-create-topic.sh --zookeeper localhost:2181 --replica 1 --partition 1 --topic test events

bin/kafka-list-topic.sh --zookeeper localhost:2181

bin/kafka-console-producer.sh --broker-list localhost:9092 --topic events

bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic events --from-beginning

Setup and Run DSE
=============================
[Fix DSE so Kafka works](https://support.datastax.com/hc/en-us/articles/204226489--java-lang-NoSuchMethodException-seen-when-attempting-Spark-streaming-from-Kafka)

Run DSE as an analytics node:
dse/bin/dse cassandra -k

To build and run the Kafka example
========================================

Build the jar file -> 'sbt assembly'
Make sure you've got a running spark server and Cassandra node listening on localhost
Make sure you've got a running Kafka server on localhost with the topic events pre-provisioned.
Start the Kafka producer sbt "runMain com.datastax.streamingDemos.KafkaProducer"
Submit the assembly to the spark server ~/dse/bin/dse spark-submit --class com.datastax.streamingDemos.KafkaConsumer ./target/scala-2.10/simpleSpark-assembly-0.2.0.jar
Data will be posted to the C* column families demo.event_log and demo.event_counters