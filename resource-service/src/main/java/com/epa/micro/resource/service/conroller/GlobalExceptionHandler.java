package com.epa.micro.resource.service.conroller;

import com.epa.micro.resource.service.model.ResourceExceptionResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResourceExceptionResponse> handleArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.error(ex);
        var wrongParamName = ex.getParameter().getParameterName();
        var wrongParamType = ex.getParameter().getParameterType().getTypeName();

        var validationError = ResourceExceptionResponse.builder()
                .errorCode(400)
                .errorMessage(String.format("Invalid value '%s' for ID. Must be a '%s'", wrongParamName, wrongParamType))
                .build();
        return ResponseEntity.badRequest().body(validationError);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ResourceExceptionResponse> handleUnsupportedMediaType(HttpMediaTypeNotSupportedException ex) {
        var contentType = ex.getContentType() != null ? ex.getContentType().toString() : "undefined";

        var validationError = ResourceExceptionResponse.builder()
                .errorCode(400)
                .errorMessage("Unsupported media type.")
                .details(List.of(String.format("Received Content-Type: '%s', but required 'audio/mpeg'", contentType)))
                .build();

        return ResponseEntity.badRequest().body(validationError);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResourceExceptionResponse> handleNoSuchElementException(NoSuchElementException ex) {
        log.error(ex);
        var validationError = ResourceExceptionResponse.builder()
                .errorCode(404)
                .errorMessage(ex.getMessage())
                .build();
        return ResponseEntity.status(404).body(validationError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResourceExceptionResponse> handleInternalServerException(Exception ex) {
        var exceptionResponse = ResourceExceptionResponse.builder()
                .errorCode(500)
                .errorMessage("Internal server error")
                .build();
        return ResponseEntity.internalServerError().body(exceptionResponse);
    }
}