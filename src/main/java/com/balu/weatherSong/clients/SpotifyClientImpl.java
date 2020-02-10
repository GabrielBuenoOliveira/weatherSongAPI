package com.balu.weatherSong.clients;

import com.balu.weatherSong.clients.interfaces.SpotifyClient;
import com.balu.weatherSong.exceptions.SpotifyException;
import com.balu.weatherSong.models.spotify.AuthorizationDto;
import com.balu.weatherSong.models.spotify.PlaylistDto;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.GET;

@Slf4j
@Component
@NoArgsConstructor
public class SpotifyClientImpl implements SpotifyClient {

    private RestTemplate restTemplate;

    @Autowired
    public SpotifyClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public AuthorizationDto getAuthToken(String client, String token) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setBasicAuth(client, token);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add(AUTH_GRANT_TYPE, AUTH_CLIENT_CREDENTIALS);
        return restTemplate.postForObject(AUTH_URL, new HttpEntity<>(map, httpHeaders), AuthorizationDto.class);
    }

    @Override
    public PlaylistDto getTrackList(String playlistId, AuthorizationDto authorizationDto) {
        HttpHeaders httpHeaders = getAuthHeaderBasedOnTokenResponse(authorizationDto);
        return restTemplate.exchange(String.format(PLAYLIST_URL, playlistId), GET, new HttpEntity<>(httpHeaders), PlaylistDto.class)
                .getBody();
    }

    HttpHeaders getAuthHeaderBasedOnTokenResponse(AuthorizationDto authorizationDto) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if ("Basic".equalsIgnoreCase(authorizationDto.getTokenType()))
            httpHeaders.setBasicAuth(authorizationDto.getAccessToken());
        else if ("Bearer".equalsIgnoreCase(authorizationDto.getTokenType()))
            httpHeaders.setBearerAuth(authorizationDto.getAccessToken());
        else
            throw new SpotifyException("Given token type is not valid");
        return httpHeaders;
    }
}
