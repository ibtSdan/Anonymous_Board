package com.example.anonymous_board.exceptionHandler;

import com.example.anonymous_board.exception.NoChangesException;
import com.example.anonymous_board.post.common.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class NoChangesExceptionHandler {
    @ExceptionHandler(value = {NoChangesException.class})
    public ResponseEntity<Api> noChangesException(NoChangesException e){
        log.error(""+e);
        var errorList = List.of(e.getMessage());
        var error = Api.Error.builder()
                .errorMessage(errorList)
                .build();
        var response = Api.builder()
                .resultCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .resultMessage(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .error(error)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
