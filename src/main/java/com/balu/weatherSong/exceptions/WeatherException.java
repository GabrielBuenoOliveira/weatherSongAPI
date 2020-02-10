package com.balu.weatherSong.exceptions;

import lombok.Getter;

public class WeatherException extends RuntimeException {

    @Getter
    private Long code;
    public WeatherException(String message) {
        super(message);
    }

    public WeatherException(Long code, String message) {
        super(message);
        this.code = code;
    }

}
