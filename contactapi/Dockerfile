FROM ubuntu:latest

# Set non-interactive mode for apt-get
ENV DEBIAN_FRONTEND=noninteractive

# Update and install required dependencies
RUN apt-get update && apt-get install -y \
    software-properties-common \
    curl \
    unzip \
    git \
    && add-apt-repository ppa:openjdk-r/ppa \
    && apt-get update \
    && apt-get install -y openjdk-21-jdk \
    maven \
    && apt-get clean

# Set JAVA_HOME environment variable
ENV JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
ENV PATH="$JAVA_HOME/bin:$PATH"
EXPOSE 8080
# Verify installations
RUN java -version && mvn -version

# Set working directory
WORKDIR /app

COPY . /app
# Default command (can be overridden in docker run)
RUN mvn install

CMD ["java","-jar","target/contactapi-0.0.1-SNAPSHOT.jar"]
