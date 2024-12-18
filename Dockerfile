FROM openjdk:11

EXPOSE 8080

ADD target/tokenizer-0.0.1-SNAPSHOT.jar tokenizer-0.0.1-SNAPSHOT.jar
ADD token_counter.py token_counter.py

ENTRYPOINT [ "java","-jar","tokenizer-0.0.1-SNAPSHOT.jar"]

