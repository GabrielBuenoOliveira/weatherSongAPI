package com.balu.weatherSong.models.spotify;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistDto {

    private List<PlaylistTrack> items;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PlaylistTrack {
        TrackDto track;
    }
}
