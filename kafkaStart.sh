#!/usr/bin/bash
cd kafka_2.11-1.1.0
echo "lol1"
./bin/zookeeper-server-start.sh config/zookeeper.properties > zookeeper &
sleep 10
./bin/kafka-server-start.sh config/server.properties  > server &
echo "lol2"
