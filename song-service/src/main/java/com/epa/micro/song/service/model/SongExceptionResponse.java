package com.epa.micro.song.service.model;

import lombok.Builder;

import java.util.List;

@Builder
public record SongExceptionResponse(int errorCode, String errorMessage, List<String> details) {
}
