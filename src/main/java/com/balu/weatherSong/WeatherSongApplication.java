package com.balu.weatherSong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.balu.weatherSong"})
public class WeatherSongApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherSongApplication.class, args);
    }
}
