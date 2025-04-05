FROM openjdk:11.0.6-jre-slim
EXPOSE 8081
ENV TZ="Asia/Ho_Chi_Minh"
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ADD bookcar-0.0.1-SNAPSHOT.jar application.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-XX:+UseSerialGC", "-Xss1m","-jar","/application.jar"]