FROM openjdk:13
ADD target/weather-song-0.0.1-SNAPSHOT.jar weather-song-0.0.1-SNAPSHOT.jar
EXPOSE 12001
ENTRYPOINT ["java","-jar","weather-song-0.0.1-SNAPSHOT.jar"]