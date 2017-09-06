import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SparkStream extends App {

  Logger.getLogger("org").setLevel(Level.OFF)
  val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Spark stream")
  val streamingContext = new StreamingContext(sparkConf, Seconds.apply(2))

  val data: ReceiverInputDStream[String] = streamingContext.receiverStream(new CustomReceiver)
  data.map((_,1)).reduceByKey(_+_).foreachRDD(rdd => println(rdd.collect.mkString("\n")))

  streamingContext.start()
  streamingContext.awaitTermination()

}
