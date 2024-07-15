package com.rts.config;

import com.rts.common.ResultCode;
import com.rts.common.ResultJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class DefaultException {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResultJson<String> defaultExceptionHandler(Exception ex){
        // 把错误信息打印在控制台上
        ex.printStackTrace();
        if (ex instanceof MyException) {
            log.error(ex.getMessage());
            return ResultJson.fail(ex.getMessage());
        }
        log.error(ex.getMessage());
        return ResultJson.custom(ResultCode.RC500.getCode(), ResultCode.RC500.getMessage(), ex.getMessage());
    }
}
