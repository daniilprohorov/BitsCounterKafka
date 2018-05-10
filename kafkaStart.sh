#!/usr/bin/bash
cd kafka_2.11-1.1.0
./bin/zookeeper-server-start.sh config/zookeeper.properties > zookeeper &
./bin/kafka-server-start.sh config/server.properties  > server &
