FROM openjdk:11

EXPOSE 8080

ADD target/prometheus-alerts-0.0.1-SNAPSHOT.jar prometheus-alerts-0.0.1-SNAPSHOT.jar

ENTRYPOINT [ "java","-jar","target/prometheus-alerts-0.0.1-SNAPSHOT.jar"]
