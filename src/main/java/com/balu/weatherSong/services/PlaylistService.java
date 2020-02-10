package com.balu.weatherSong.services;

import com.balu.weatherSong.exceptions.WeatherException;
import com.balu.weatherSong.models.PlaylistStyle;
import com.balu.weatherSong.models.spotify.PlaylistDto;
import com.balu.weatherSong.models.weather.TemperatureInfoDto;
import com.balu.weatherSong.models.weather.WeatherDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PlaylistService {

    private SpotifyService spotifyService;
    private WeatherService weatherService;

    @Autowired
    public PlaylistService(SpotifyService spotifyService, WeatherService weatherService, CacheService cacheService) {
        this.spotifyService = spotifyService;
        this.weatherService = weatherService;
    }

    public List<PlaylistDto.PlaylistTrack> getPlayListSuggestion(Pair<Double, Double> coord) {
        WeatherDto weatherDto = weatherService.getWeather(coord.getFirst(), coord.getSecond());
        PlaylistStyle style = matchStyle(weatherDto);
        return spotifyService.getPlaylist(style.code).getItems();
    }

    public List<PlaylistDto.PlaylistTrack> getPlayListSuggestion(String cityName) {
        WeatherDto weatherDto = weatherService.getWeather(cityName);
        PlaylistStyle style = matchStyle(weatherDto);

        return spotifyService.getPlaylist(style.code).getItems();
    }


    private PlaylistStyle matchStyle(WeatherDto weatherInfo) {
        return Optional.ofNullable(weatherInfo.getMain())
                .map(TemperatureInfoDto::getTemp)
                .map(temp -> {
                    if (temp > 30) {
                        log.info("Temperature is higher than 30 (" + temp + ") sending PARTY");
                        return PlaylistStyle.PARTY_MUSIC;
                    } else if (temp >= 15) {
                        log.info("Temperature is between 15 and 30 (" + temp + ") sending POP");
                        return PlaylistStyle.POP_MUSIC;
                    } else if (temp >= 10) {
                        log.info("Temperature is between 10 and 14 (" + temp + ") sending ROCK");
                        return PlaylistStyle.ROCK_MUSIC;
                    } else {
                        log.info("Temperature is lower than 10 (" + temp + ") sending CLASSICAL");
                        return PlaylistStyle.CLASSICAL_MUSIC;
                    }
                }).orElseThrow(() -> new WeatherException("Err trying  get the weather for this location"));
    }
}
