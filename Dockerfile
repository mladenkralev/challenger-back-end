FROM adoptopenjdk/openjdk11:aarch64-ubuntu-jdk-11.0.11_9

EXPOSE 8080

COPY ./build/libs/challenger-backend.jar /usr/app/
WORKDIR /usr/app

ENTRYPOINT ["java", "-jar", "challenger-backend.jar"]