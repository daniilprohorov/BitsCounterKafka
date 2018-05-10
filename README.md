# BitsCounterKafka
Specs: calculate sum of "1" from flow "0" and "1". Kafka producer --> Kafka --> Apache flink (consumer) --> console output
  
  ## How to use:
  
  In terminal start zookeeper server:
  ```bash
  $ cd BitsCounterKafka/kafka_2.11-1.1.0/
  ./bin/zookeeper-server-start.sh config/zookeeper.properties
  ```
  
  In new terminal start kafka-server:
  ```bash
  $ cd BitsCounterKafka/kafka_2.11-1.1.0/
  ./bin/kafka-server-start.sh config/server.properties
  ```
  
  In new terminal create topic(only once):
  ```bash
  $ cd BitsCounterKafka/kafka_2.11-1.1.0/
  ./bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic bits
  ```
  
  In new terminal start kafka producer:
  ```bash
  $ cd BitsCounterKafka/
  java -jar Producer-assembly-0.1.jar
  ```
  
  In new terminal start kafka consumer:
  ```bash
  $ cd BitsCounterKafka/
  java -jar Consumer-assembly-0.1.jar
  ```
  
  
  
  Producer src folder - /c
  Consumer src folder - /p
  

  
