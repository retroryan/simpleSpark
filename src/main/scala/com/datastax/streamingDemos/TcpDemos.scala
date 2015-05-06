package com.datastax.streamingDemos

import java.io.PrintStream
import java.net.ServerSocket

//
// to stream data into the cluster open up netcat and echo sample records to it, one per line.
//
// nc -lk 9999
// 2014-10-07T12:20:09Z;foo;1
// 2014-10-07T12:21:09Z;foo;29
// 2014-10-07T12:22:10Z;foo;1
// 2014-10-07T12:23:11Z;foo;29


object TcpConsumer {
  def main(args: Array[String]) {
    val (sc, ssc, cc) = StreamConsumer.setup()
    val input = ssc.socketTextStream(Config.tcpHost, Config.tcpPort)
    StreamConsumer.process(ssc, input)
  }
}


object TcpProducer {
  def main(args: Array[String]) {
    val server = new ServerSocket(Config.tcpPort)

    while (true) {
      val socket = server.accept()
      val outstream = new PrintStream(socket.getOutputStream())
      while (!outstream.checkError()) {
        val event = EventGenerator.generateEvent()
        println(event)
        outstream.println(event)
        Thread.sleep(100)
      }
      socket.close()
    }
  }
}