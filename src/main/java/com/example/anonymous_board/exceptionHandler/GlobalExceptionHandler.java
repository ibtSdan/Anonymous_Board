package com.example.anonymous_board.exceptionHandler;

import com.example.anonymous_board.post.common.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Order(value = Integer.MAX_VALUE) // 가장 마지막
@Slf4j

public class GlobalExceptionHandler {
    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<Api> globalException(Exception e){
        log.error(""+e);
        var errorList = List.of(e.getMessage());
        var error = Api.Error.builder()
                .errorMessage(errorList)
                .build();
        var response = Api.builder()
                .resultCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .resultMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .error(error)
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
