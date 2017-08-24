# Eventbus Example

This example illustrates how Vert.x applications can interacts using the event bus.

Two applications are deployed:

1. A data producer generates random data periodically and sends it on the event bus
2. A data consumer consumes this data and propagate it to the web ui using SockJS.



## OpenShift build

```bash
cd data-producer
mvn clean fabric8:deploy -Popenshift

cd ../data-consumer
mvn clean fabric8:deploy -Popenshift
```

In your browser, open the route exposed by the data-consumer, such as 
http://data-consumer-myproject.192.168.64.12.nip.io.

