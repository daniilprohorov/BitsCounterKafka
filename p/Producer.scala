import scala.util.control.Breaks._
import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import scala.util.Random

object KafkaProducer extends App {

  val topic = "bits"
  val props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")
  props.put("linger.ms", "500")
  props.put("batch.size", "1")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")


  val rnd = new Random()

  val producer = new KafkaProducer[String,String](props)
  var key  = 0

  def whl(){
    Thread.sleep(500)
    val index = rnd.nextInt(2)
    producer.send(new ProducerRecord(topic, key.toString, index.toString))
    key =  key + 1
    //if(System.in.read == 113){
    //  return ()
    //  }
    whl()
    }
    whl()
  producer.close
}
