FROM openjdk:17

EXPOSE 8088

ADD fluentd-sample-0.0.1-SNAPSHOT.jar fluentd-sample-0.0.1-SNAPSHOT.jar 

ENTRYPOINT [ "java","-jar","fluentd-sample-0.0.1-SNAPSHOT.jar"]