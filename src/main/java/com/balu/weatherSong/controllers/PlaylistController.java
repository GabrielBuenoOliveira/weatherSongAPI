package com.balu.weatherSong.controllers;

import com.balu.weatherSong.services.PlaylistService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    private PlaylistService service;

    @Autowired
    public PlaylistController(PlaylistService playListService) {
        service = playListService;
    }

    @GetMapping
    public ResponseEntity getPlaylist(@RequestParam(required = false) Double lat,
                                      @RequestParam(required = false) Double lon,
                                      @RequestParam(required = false) String cityName) {

        if ((lat != null && lon != null))
            return ResponseEntity.ok(service.getPlayListSuggestion(Pair.of(lat, lon)));
        else if (isNotBlank(cityName))
            return ResponseEntity.ok(service.getPlayListSuggestion(cityName));
        return ResponseEntity.badRequest().body("Must be given lat, lon or cityName ");
    }

}
