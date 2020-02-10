package com.balu.weatherSong.models.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrackDto {
    @JsonProperty("external_urls")
    private ExternalDto link;
    private String name;
    private AlbumDto album;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AlbumDto {
        @JsonProperty("external_urls")
        private ExternalDto link;
        private String name;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ExternalDto {
        @JsonProperty("spotify")
        private String link;
    }
}
