# Song based on wather API

This server returns a spotify list of tracks based on the weather.
The business rule are:
* Temperature > 30 / PARTY songs
* Temperature between 15 and 30 / POP songs
* Temperature between 10 and 14 / ROCK songs 
* Temperature < 10 / CLASSICAL songs

## Build and run
First of all, is necessary build the application.

`$ mvn package`

Creates an application docker image running

`$ docker build -f Dockerfile -t [APP_NAME] .`

In the end, to run the application:

`$ docker-compose up`