package com.balu.weatherSong.clients;

import com.balu.weatherSong.clients.interfaces.WeatherClient;
import com.balu.weatherSong.models.weather.WeatherDto;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@Slf4j
@Component
@NoArgsConstructor
public class WeatherClientImpl implements WeatherClient {

    private RestTemplate restTemplate;

    @Autowired
    public WeatherClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public WeatherDto getWeatherByCoordinates(Double lat, Double lon, String apiKey) {
        log.debug("Getting weather info using lat and lon parameters");
        String uri = addRequestParam(Map.of("lat", lat, "lon", lon, "units", "metric", "APPID", apiKey));
        return restTemplate.getForObject(uri, WeatherDto.class);
    }

    @Override
    public WeatherDto getWeatherByCityName(String name, String apiKey) {
        log.debug("Getting weather info using city name parameter");
        String uri = addRequestParam(Map.of("q", name, "units", "metric", "APPID", apiKey));
        return restTemplate.getForObject(uri, WeatherDto.class);
    }

    /**
     * Given an {@link Map} with the key is the property and value is the parameter to be added
     * in {@link WeatherClient#WEATHER_API_URL} request.
     */
    String addRequestParam(Map<String, Object> params) {
        UriComponentsBuilder builder = fromUriString(WEATHER_API_URL);
        params.keySet().forEach(k ->
                builder.queryParam(k, params.get(k)));
        return builder.toUriString();
    }
}
