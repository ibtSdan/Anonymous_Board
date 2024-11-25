package com.example.anonymous_board.exceptionHandler;

import com.example.anonymous_board.post.common.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@Order(1)
public class ValidationExceptionHandler {
    @ExceptionHandler(value = { MethodArgumentNotValidException.class})
    public ResponseEntity<Api> validationException(MethodArgumentNotValidException e){
        log.error(""+e);
        var errorMessageList = e.getFieldErrors().stream()
                .map( it -> {
                    var format = "%s : %s 는 %s";
                    return String.format(format,it.getField(),it.getRejectedValue(),it.getDefaultMessage());
                }).toList();
        var error = Api.Error.builder().errorMessage(errorMessageList).build();
        var response = Api.builder()
                .resultCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .resultMessage(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .error(error)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
