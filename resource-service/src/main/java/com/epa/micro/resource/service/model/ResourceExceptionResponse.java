package com.epa.micro.resource.service.model;

import lombok.Builder;

import java.util.List;

@Builder
public record ResourceExceptionResponse(int errorCode, String errorMessage, List<String> details) {
}
