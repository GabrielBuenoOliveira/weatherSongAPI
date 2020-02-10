package com.balu.weatherSong.clients;

import com.balu.weatherSong.exceptions.SpotifyException;
import com.balu.weatherSong.models.spotify.AuthorizationDto;
import com.balu.weatherSong.models.spotify.PlaylistDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RunWith(MockitoJUnitRunner.class)
public class SpotifyClientImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private SpotifyClientImpl spotifyClient;

    @Mock
    private AuthorizationDto authorizationDto;

    @Mock
    private PlaylistDto playlistDto;

    @Before
    public void setup() {
        when(authorizationDto.getAccessToken()).thenReturn("Token");
    }

    @Test(expected = SpotifyException.class)
    public void shouldThrowWithInvalidAuthType() {
        when(authorizationDto.getTokenType()).thenReturn("Type");
        spotifyClient.getAuthHeaderBasedOnTokenResponse(authorizationDto);
    }

    @Test
    public void shouldGetBasicHeader() {
        when(authorizationDto.getTokenType()).thenReturn("Basic");
        HttpHeaders headers = spotifyClient.getAuthHeaderBasedOnTokenResponse(authorizationDto);
        assertTrue(headers.get(AUTHORIZATION).get(0).contains("Basic"));
    }

    @Test
    public void shouldGetBearerHeader() {
        when(authorizationDto.getTokenType()).thenReturn("Bearer");
        HttpHeaders headers = spotifyClient.getAuthHeaderBasedOnTokenResponse(authorizationDto);
        assertTrue(headers.get(AUTHORIZATION).get(0).contains("Bearer"));
    }
}