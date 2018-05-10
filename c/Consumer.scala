import java.util.Properties
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.extensions._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.connectors.kafka._
import org.apache.flink.streaming.util.serialization.SimpleStringSchema

import org.apache.flink.streaming.api.windowing.triggers.{CountTrigger, PurgingTrigger}
import org.apache.flink.streaming.api.windowing.assigners.{SlidingProcessingTimeWindows}
import org.apache.flink.streaming.api.windowing.evictors.{CountEvictor}
import org.apache.flink.streaming.api.windowing.assigners.GlobalWindows
import org.apache.flink.streaming.api.windowing.windows.GlobalWindow


object KafkaConsumer {

  def main(args: Array[String]) {

    /** Name of topic */
    val topic = "bits" 

    /** environment */
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    /** Properties for Apache Kafka */ 
    val properties = new Properties()
    properties.setProperty("bootstrap.servers", "localhost:9092")

    // id of the consumer group
    properties.setProperty("group.id", "test")
    val myConsumer = new FlinkKafkaConsumer011[String](topic, new SimpleStringSchema(), properties)

    myConsumer.setStartFromEarliest()
    val stream = env.addSource(myConsumer)

    stream.print

    /** Было очень не просто написать эту функцию, а все почему? А потому что
     *  стандартная countWindow считает количество ключей элемента, а не 
     *  количество элементов. Тоесть у нас всегда будет 0 или какое-то 
     *  определенное количество 1 на выходе, а это не правильно, поэтому 
     *  пришлось написать эту функцию. Вполне возможно я просто что-то не нашел
     *  и решение было очевидно, но я ничего не нашел, поэтомы вышел из ситуации
     *  как смог. Сейчас все считается правильно! Чему я несказанно рад!! */ 

    def countsWindow(stream : DataStream[String], size : Int, slide : Int):DataStream[(String, Int)] = {
          stream.map{ x => (x, x.toInt) }
                .windowAll(GlobalWindows.create())
                .evictor(CountEvictor.of(size))  
                .trigger(CountTrigger.of(slide))
                .sum(1)
    }
    countsWindow(stream, 6, 2).print 
    
    env.execute()
  }    
}    
