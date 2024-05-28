package com.wcc.exceptions.handler;

import com.wcc.entity.enums.MessageCode;
import com.wcc.exceptions.NotFoundException;
import com.wcc.models.responses.BaseResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class WccExceptionHandler {
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NotFoundException.class)
    public BaseResponse notFound(NotFoundException e) {
        log.error("Not found exception", e);
        return BaseResponse.builder()
                .code(MessageCode.NOT_FOUND)
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = Exception.class)
    public BaseResponse badRequest(Exception e) {
        log.error("Bad request exception", e);
        return BaseResponse.builder()
                .code(MessageCode.BAD_REQUEST)
                .message(e.getMessage())
                .build();
    }
}
