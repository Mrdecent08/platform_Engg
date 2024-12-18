FROM openjdk:11

EXPOSE 8080

ADD target/ARMetrics-0.0.1-SNAPSHOT.jar ARMetrics-0.0.1-SNAPSHOT.jar

ENTRYPOINT [ "java","-jar","ARMetrics-0.0.1-SNAPSHOT.jar"]

