package com.wzy.quanyoumall.product.exception;

import com.wzy.quanyoumall.common.constant.bizCodeEnum;
import com.wzy.quanyoumall.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice(basePackages = "com.wzy.quanyoumall.product.controller")
public class productExceptionAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleExceptiona(MethodArgumentNotValidException e) {
        log.info("异常:{},异常类型{}", e, e.getClass());
        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> errorMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach((fieldError) -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return R.error(bizCodeEnum.VALID_EXCEPTION.getCode(), bizCodeEnum.VALID_EXCEPTION.getMsg()).put("data", errorMap);
    }

    @ExceptionHandler(value = Throwable.class)
    public R handleExceptionb(Exception e) {
        e.printStackTrace();
        log.error(e.getMessage(), e.getStackTrace(), e.getClass());
        return R.error(bizCodeEnum.UNKNOW_EXCEPTION.getCode(), bizCodeEnum.UNKNOW_EXCEPTION.getMsg());
    }
}
