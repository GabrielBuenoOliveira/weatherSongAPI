package com.balu.weatherSong.clients.interfaces;

import com.balu.weatherSong.models.weather.WeatherDto;

public interface WeatherClient {
    String WEATHER_API_URL = "http://api.openweathermap.org/data/2.5/weather";

    WeatherDto getWeatherByCoordinates(Double lat, Double lon, String apiKey);

    WeatherDto getWeatherByCityName(String name, String apiKey);
}
