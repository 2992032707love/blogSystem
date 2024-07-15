FROM openjdk:17-jdk-slim
MAINTAINER rts<rents@foxmail.com>

VOLUME /tmp
ADD blogSystem-1.0-SNAPSHOT-spring-boot.jar rts_docker.jar
RUN bash -c 'touch /rts_docker.jar'
ENTRYPOINT ["java","-jar","/rts_docker.jar"]

EXPOSE 9401