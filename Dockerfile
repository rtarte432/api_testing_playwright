FROM azul/zulu-openjdk:17-jre
EXPOSE 8080
ADD target/demo-0.0.1-SNAPSHOT.jar demo-0.0.1-SNAPSHOT.jar
ADD logback-spring.xml logback-spring.xml
ENTRYPOINT ["java","-jar","/demo-0.0.1-SNAPSHOT.jar","--logging.config=logback-spring.xml"]