FROM openjdk:11

EXPOSE 8080

ADD target/bucket-report-0.0.1-SNAPSHOT.jar bucket-report-0.0.1-SNAPSHOT.jar 

ENTRYPOINT [ "java","-jar","bucket-report-0.0.1-SNAPSHOT.jar"]


