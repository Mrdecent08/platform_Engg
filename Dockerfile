FROM openjdk:11

EXPOSE 8080

ADD target/aws_dynamic_volume-0.0.1-SNAPSHOT.jar aws_dynamic_volume-0.0.1-SNAPSHOT.jar 

ENTRYPOINT [ "java","-jar","aws_dynamic_volume-0.0.1-SNAPSHOT.jar"]

