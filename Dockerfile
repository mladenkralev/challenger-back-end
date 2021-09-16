FROM arm32v7/adoptopenjdk:11-jdk

EXPOSE 8080

COPY ./build/libs/challenger-backend.jar /usr/app/
WORKDIR /usr/app

ENTRYPOINT ["java", "-jar", "challenger-backend.jar"]