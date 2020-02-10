package com.balu.weatherSong.exceptions;

import lombok.Getter;

public class SpotifyException extends RuntimeException {

    @Getter
    private Long code;

    public SpotifyException(String message) {
        super(message);
    }

    public SpotifyException(Long code, String message) {
        super(message);
        this.code = code;
    }

}