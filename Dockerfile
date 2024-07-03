FROM openjdk:11

EXPOSE 8080

ADD target/alert-hook-0.0.1-SNAPSHOT.jar alert-hook-0.0.1-SNAPSHOT.jar

ENTRYPOINT [ "java","-jar","alert-hook-0.0.1-SNAPSHOT"]
