package com.balu.weatherSong.clients.interfaces;

import com.balu.weatherSong.models.spotify.AuthorizationDto;
import com.balu.weatherSong.models.spotify.PlaylistDto;

public interface SpotifyClient {

    String PLAYLIST_URL = "https://api.spotify.com/v1/playlists/%s/tracks?fields=items(track(name,external_urls,album(name,external_urls)))";
    String AUTH_URL = "https://accounts.spotify.com/api/token";
    String AUTH_GRANT_TYPE = "grant_type";
    String AUTH_CLIENT_CREDENTIALS = "client_credentials";

    PlaylistDto getTrackList(String playlistId, AuthorizationDto authorizationDto);

    AuthorizationDto getAuthToken(String client, String token);
}
