FROM adoptopenjdk/openjdk11

EXPOSE 8080

COPY ./build/libs/challenger-backend.jar /usr/app/
WORKDIR /usr/app

ENTRYPOINT ["java", "-jar", "challenger-backend.jar"]