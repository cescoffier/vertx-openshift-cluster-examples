# Shared Coutner Example

This example illustrate how Vert.x applications can share a counter.


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
