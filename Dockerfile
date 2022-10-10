FROM openjdk:8-jdk-alpine

EXPOSE 8084

ADD target/ddms-ppn-cmd-0.0.1-SNAPSHOT.jar ddms-ppn-cmd-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","ddms-ppn-cmd-0.0.1-SNAPSHOT.jar"]

