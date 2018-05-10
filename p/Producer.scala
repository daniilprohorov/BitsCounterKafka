/** Предыстория - долго очень не мог разобраться с flink producer
 *  поэтому в итоге нашел информацию по kafka producer на scala 
 *  и сделал так, но оказалось, что все мио проблемы в flink были 
 *  из-за подлкюченной библиотеки DataSet( А это нельзя делать, 
 *  когда у тебя DataStream'). Поэтому если что, то producer можно 
 *  написать и использую flink, хотя тут опять же встает вопрос - 
 *  насколько это нужно, для простой генерации 0 и 1'*/
import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import scala.util.Random

object KafkaProducer extends App {

  val topic = "bits"
  val props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")
  /** Delay betwin sending batch */ 
  props.put("linger.ms", "500") // dont work( 

  /** Size of one batch */
  props.put("batch.size", "1")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  val rnd = new Random()
  val producer = new KafkaProducer[String,String](props)
  var key  = 0
  
  /** loop cycle */
  def whl(){
    /** Delay betwin sending */
    Thread.sleep(500)
    /** generate 0 or 1 */
    val bit = rnd.nextInt(2)
    /** Send bit with key to topic */
    producer.send(new ProducerRecord(topic, key.toString, bit.toString))
    key =  key + 1

    /** Честно говоря особо не знаю как сделать нормальное прерывание
     *  в консольном приложении, поэтому было реализовано как-то так:
     *  при нажатии "q" выходим из бесконечного цикла, но тут понятное
     *  дело возникают проблемы, потому что для того, чтобы отправить 
     *  бит приходится нажимать enter каждый раз, возможно нужно найти
     *  что-то типа ncurces но я особо в это не вникал, потому что для 
     *  тестового задания думаю и так будет работать*/
    
    /** if(System.in.read == 113){
      producer.send(new ProducerRecord(topic, key.toString, "9"))
      return ()
      } */
    whl()
    }

  whl()
  producer.close
}
