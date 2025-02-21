package com.epa.micro.song.service.service;

public class SongExistsException extends RuntimeException{
    public SongExistsException(String message) {
        super(message);
    }
}
