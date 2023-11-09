FROM openjdk:17

EXPOSE 8080

ADD secret-management.jar secret-management.jar 

ENTRYPOINT [ "java","-jar","secret-management.jar"]