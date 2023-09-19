package com.sky.exception;

import com.sky.constant.MessageConstant;
import com.sky.constant.ErrorCodeConstant;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 捕获sql异常
     * @param ex
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<?> handleSqlIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex){
        log.error("异常信息：{}", ex.getMessage());
        String message = ex.getMessage();
        if (message.contains("Duplicate entry")) {
            String[] split = message.split(" ");
            String name = split[2];
            return Result.error(ErrorCodeConstant.SQL_INTEGRITY_CONSTRAINT_VIOLATION, name + MessageConstant.ALREADY_EXISTS);
        }
        return Result.error(MessageConstant.UNKNOWN_ERROR);
    }

    // 可以为每个自定义异常都定义一个逻辑，client就可以拿到 MessageConstant.ACCOUNT_NOT_FOUND
    @ExceptionHandler(AccountNotFoundException.class)
    public Result<?> handleAccountNotFoundException(AccountNotFoundException ex) {
        log.error("Account not found error msg: {}", ex.getMessage());
        return Result.error(ErrorCodeConstant.ACCOUNT_NOT_FOUND, MessageConstant.ACCOUNT_NOT_FOUND);
//        return Result.error(MessageConstant.ACCOUNT_NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public Result<?> handleGenericException(Exception ex) {
        log.error("未知异常 Error msg: {}", ex.getMessage());
        return Result.error(ErrorCodeConstant.GENERIC_ERROR, MessageConstant.UNKNOWN_ERROR);
    }
}
