import java.util.Properties
//import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.connectors.kafka._
import org.apache.flink.streaming.util.serialization.SimpleStringSchema

object KafkaConsumer {


  def main(args: Array[String]) {

    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val properties = new Properties()
    // comma separated list of Kafka brokers
    properties.setProperty("bootstrap.servers", "localhost:9092")
    // id of the consumer group
    properties.setProperty("group.id", "test")
    val myConsumer = new FlinkKafkaConsumer011[String]("bits", new SimpleStringSchema(), properties)
    myConsumer.setStartFromEarliest()
    val stream = env
      // words is our Kafka topic
      .addSource(myConsumer)

    stream.print
     val counts = stream
      .map{ x => (x, x.toInt) }
      .keyBy(0)
      .timeWindow(Time.seconds(5))
      //.countWindow(10)
      //.map(_.toInt)
      //.toInt
      .sum(1)

    counts.print

    env.execute("Kafka Window Stream WordCount")
  }
}
