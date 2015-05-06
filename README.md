Simple Spark Project Example - Not the recommended approach!
========================================================

Example of a Simple Spark project that bundles into a single jar and runs the specified main. This is not the recommended approach to running a spark project. 

Look at using Spark Submit (spark submit --class) or the [Spark Job Server](https://github.com/spark-jobserver/spark-jobserver) instead.

How to Run
==========

* checkout source
* Change the IP to point to your client application IP (where this program runs) and server IP address in DemoApp.scala
* brew install sbt
* In the command prompt: 
* sbt assembly
* java -jar target/scala-2.10/simpleSpark-assembly-0.2.0.jar 
* Change the main that gets run by changing the mainClass in the build.sbt file 
