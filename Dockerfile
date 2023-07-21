FROM mcr.microsoft.com/playwright/java:v1.35.1-jammy
EXPOSE 8080
COPY . /
ENTRYPOINT ["mvn", "install", "-DskipTests"]
ENTRYPOINT ["mvn", "surefire-report:report"]