FROM arm64v8/eclipse-temurin:11.0.12_7-jdk-focal

EXPOSE 8080

COPY ./build/libs/challenger-backend.jar /usr/app/
WORKDIR /usr/app

ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "challenger-backend.jar"]