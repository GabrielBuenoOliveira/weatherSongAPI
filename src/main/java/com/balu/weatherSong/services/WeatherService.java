package com.balu.weatherSong.services;

import com.balu.weatherSong.clients.interfaces.WeatherClient;
import com.balu.weatherSong.models.weather.WeatherDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.balu.weatherSong.services.CacheService.CACHE_HIT;
import static com.balu.weatherSong.services.CacheService.CACHE_MISS;

@Slf4j
@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.ttl}")
    private Long ttl;

    private final String WEATHER_KEY = "Weather=";

    private WeatherClient weatherClient;
    private CacheService cacheService;

    @Autowired
    public WeatherService(WeatherClient weatherClient, CacheService cacheService) {
        this.weatherClient = weatherClient;
        this.cacheService = cacheService;
    }

    public WeatherDto getWeather(Double lat, Double lon) {
        String key = WEATHER_KEY + lat + '-' + "lon";
        if (cacheService.isPresent(key)) {
            log.info(CACHE_HIT + "Weather for lat=" + lat + "lon=" + lon);
            return cacheService.getValue(key, WeatherDto.class);
        } else {
            log.info(CACHE_MISS + "Weather for lat=" + lat + "lon=" + lon);
            WeatherDto weatherDto = weatherClient.getWeatherByCoordinates(lat, lon, apiKey);
            cacheService.setValue(key, weatherDto, ttl);
            return weatherDto;
        }
    }

    public WeatherDto getWeather(String cityName) {
        String key = WEATHER_KEY + cityName;
        if (cacheService.isPresent(key)) {
            log.info(CACHE_HIT + "Weather for " + cityName);
            return cacheService.getValue(key, WeatherDto.class);
        } else {
            log.info(CACHE_MISS + "Weather for " + cityName);
            WeatherDto weatherDto = weatherClient.getWeatherByCityName(cityName, apiKey);
            cacheService.setValue(key, weatherDto, ttl);
            return weatherDto;
        }
    }
}
