package com.datastax.streamingDemos

import java.util.{Random, TimeZone}

object EventGenerator {

  val eventNames = Array("thyrotome", "radioactivated", "toreutics", "metrological",
    "adelina", "architecturally", "unwontedly", "histolytic", "clank", "unplagiarised",
    "inconsecutive", "scammony", "pelargonium", "preaortic", "goalmouth", "adena",
    "murphy", "vaunty", "confetto", "smiter", "chiasmatype", "fifo", "lamont", "acnode",
    "mutating", "unconstrainable", "donatism", "discept")

  def currentTimestamp() : String = {
    val tz = TimeZone.getTimeZone("UTC")
    val sdf = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    sdf.setTimeZone(tz)
    val dateString = sdf.format(new java.util.Date)
    return dateString
  }

  def randomEventName() : String = {
    val rand = new Random(System.currentTimeMillis())
    val random_index = rand.nextInt(eventNames.length)
    return eventNames(random_index)
  }

  def generateEvent() : String = {
    // message in the form of "2014-10-07T12:20:08Z;foo;1"
    val eventCount = scala.util.Random.nextInt(10).toString()
    return currentTimestamp() + ";" + randomEventName() + ";" + eventCount
  }
}