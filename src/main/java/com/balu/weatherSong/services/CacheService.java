package com.balu.weatherSong.services;

public interface CacheService {

    final String CACHE_HIT = "[CACHE HIT] ";
    final String CACHE_MISS = "[CACHE MISS] ";

    <T> void setValue(String key, T value, Long ttlInSeconds);

    <T> T getValue(String key, Class<T> responseType);

    boolean isPresent(String key);
}
