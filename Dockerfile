FROM mcr.microsoft.com/playwright/java:v1.35.1-jammy
EXPOSE 8080
ADD target/demo-0.0.1-SNAPSHOT.jar demo-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["jar","-xf","demo-0.0.1-SNAPSHOT.jar","-C","extracted-directory"]
ENTRYPOINT ["cd","extracted-directory"]
ENTRYPOINT ["mvn", "test"]
ENTRYPOINT ["rm","-rf","extracted-directory"]