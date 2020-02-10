package com.balu.weatherSong.models.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDto {

    private String name;
    private CoordinatesDto coord;
    private TemperatureInfoDto main;
    private WindDto wind;
}
