# Shared Counter Example

This example illustrates how Vert.x applications can share a counter.


## Local build and run

```bash
mvn clean package

# In a first shell
java -Djava.net.preferIPv4Stack=true -jar target/shared-counter-example-1.0-SNAPSHOT.jar
# In a second shell
java -Djava.net.preferIPv4Stack=true -jar target/shared-counter-example-1.0-SNAPSHOT.jar --conf="{\"port\":8081}" 
```

Once launched, open your browser to:

1. http://localhost:8080
2. http://localhost:8081

## OpenShift build

```bash
mvn clean fabric8:deploy -Popenshift
```

Once deployed, set the number of replicas to 2 with:
```bash
oc scale dc shared-counter-example --replicas=2
```

Use curl as follows (update route url):

```bash
curl http://shared-counter-example-myproject.192.168.64.12.nip.io/counter/inc
curl http://shared-counter-example-myproject.192.168.64.12.nip.io/counter/inc
```

You should see the request handled by the 2 nodes and incrementing the counter accordingly.