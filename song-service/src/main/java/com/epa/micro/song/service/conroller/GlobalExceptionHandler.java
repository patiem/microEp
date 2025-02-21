package com.epa.micro.song.service.conroller;

import com.epa.micro.song.service.model.SongExceptionResponse;
import com.epa.micro.song.service.service.SongExistsException;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<SongExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error(ex);
        var errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        var validationError = SongExceptionResponse.builder()
                .errorCode(400)
                .errorMessage("Validation error")
                .details(errors)
                .build();
        return ResponseEntity.badRequest().body(validationError);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<SongExceptionResponse> handleNoSuchElementException(NoSuchElementException ex) {
        log.error(ex);
        var validationError = SongExceptionResponse.builder()
                .errorCode(404)
                .errorMessage(ex.getMessage())
                .build();
        return ResponseEntity.status(404).body(validationError);
    }

    @ExceptionHandler(SongExistsException.class)
    public ResponseEntity<SongExceptionResponse> handleSongExistsException(SongExistsException ex) {
        log.error(ex);
        var validationError = SongExceptionResponse.builder()
                .errorCode(409)
                .errorMessage(ex.getMessage())
                .build();
        return ResponseEntity.status(409).body(validationError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<SongExceptionResponse> handleInternalServerException(Exception ex) {
        var exceptionResponse = SongExceptionResponse.builder()
                .errorCode(500)
                .errorMessage("Internal server error")
                .build();
        return ResponseEntity.internalServerError().body(exceptionResponse);
    }
}