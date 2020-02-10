package com.balu.weatherSong.clients;

import com.balu.weatherSong.models.weather.WeatherDto;
import org.apache.commons.validator.routines.UrlValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static com.balu.weatherSong.clients.interfaces.WeatherClient.WEATHER_API_URL;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WeatherClientImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherClientImpl weatherClient;

    @Mock
    private WeatherDto responseDto;

    @Test
    public void shouldAddParam() {
        Map<String, Object> map = Map.of("testString", "Value",
                "testDouble", 23D);
        String response = weatherClient.addRequestParam(map);

        assertTrue(response.contains(WEATHER_API_URL));
        assertTrue(response.contains("testString=Value"));
        assertTrue(response.contains("testDouble=23"));
        assertTrue(new UrlValidator().isValid(response));
    }

    @Test
    public void shouldTestGetWeatherByCoordinates() {
        weatherClient.getWeatherByCoordinates(23D, 23D, "Test");
        String uri = weatherClient.addRequestParam(Map.of("lat", 23D, "lon", 23D, "units", "metric", "APPID", "Test"));
        verify(restTemplate, times(1)).getForObject(uri, WeatherDto.class);
    }

    @Test
    public void shouldTestGetWeatherByCityName() {
        weatherClient.getWeatherByCityName("TestCity", "Test");
        String uri = weatherClient.addRequestParam(Map.of("q", "TestCity", "units", "metric", "APPID", "Test"));
        verify(restTemplate, times(1)).getForObject(uri, WeatherDto.class);
    }

}