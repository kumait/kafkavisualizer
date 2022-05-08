# Kafka Visualizer

Kafka Visualizer is a Kafka GUI client.

It is mainly used to monitor and produce messages in Kafka Clusters for debugging purposes. 

![](docs/screenshots/light.png)

![](docs/screenshots/dark.png)

## Download

Kafka Visualizer is distributed as an executable JAR file, you can download the last release from the releases page.

You need Java 11 or newer.

```bash
java -jar kafkavisualizer-1.x.x.jar
```

### Linux Display Scaling

On Linux, you might need to use the below command if you are using display scaling.

```bash
java -Dsun.java2d.uiScale=2 -jar kafkavisualizer-1.x.x.jar
```
