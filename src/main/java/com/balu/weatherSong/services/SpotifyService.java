package com.balu.weatherSong.services;

import com.balu.weatherSong.clients.interfaces.SpotifyClient;
import com.balu.weatherSong.models.spotify.AuthorizationDto;
import com.balu.weatherSong.models.spotify.PlaylistDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.balu.weatherSong.services.CacheService.CACHE_HIT;
import static com.balu.weatherSong.services.CacheService.CACHE_MISS;

@Slf4j
@Service
public class SpotifyService {

    @Value("${spotify.api.client}")
    private String client;

    @Value("${spotify.api.key}")
    private String token;

    @Value("${spotify.ttl}")
    private Long ttl;

    private final String SPOTIFY_KEY = "Spotify=";
    private final Long SAFETY_TIME = 300L; // must be in seconds

    private final SpotifyClient spotifyClient;
    private CacheService cacheService;

    @Autowired
    public SpotifyService(SpotifyClient spotifyClient, CacheService cacheService) {
        this.spotifyClient = spotifyClient;
        this.cacheService = cacheService;
    }

    public PlaylistDto getPlaylist(String playlistId) {
        String key = SPOTIFY_KEY + "playlist-" + playlistId;
        AuthorizationDto authorizationDto = getAuthorizationToken();
        if (cacheService.isPresent(key)) {
            log.info(CACHE_HIT + "playlist(" + playlistId + ") request");
            return cacheService.getValue(key, PlaylistDto.class);
        } else {
            log.info(CACHE_MISS + "playlist(" + playlistId + ") request");
            PlaylistDto playlistDto = spotifyClient.getTrackList(playlistId, authorizationDto);
            cacheService.setValue(key, playlistDto, ttl);
            return playlistDto;
        }
    }

    /**
     * For this method there is a different ttl for auth because the response contains
     * when the token expires. Just for safety
     */
    public AuthorizationDto getAuthorizationToken() {
        String key = SPOTIFY_KEY + "auth";
        if (cacheService.isPresent(key)) {
            log.info(CACHE_HIT + "auth request");
            return cacheService.getValue(key, AuthorizationDto.class);
        } else {
            log.info(CACHE_MISS + "auth request");
            AuthorizationDto authorizationDto = spotifyClient.getAuthToken(client, token);
            cacheService.setValue(key, authorizationDto, authorizationDto.getExpires() - SAFETY_TIME);
            return authorizationDto;
        }
    }
}
