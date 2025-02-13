package com.wzy.quanyoumall.search.exception;

import com.wzy.quanyoumall.common.exception.bizCodeEnum;
import com.wzy.quanyoumall.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@Slf4j
@RestControllerAdvice(basePackages = "com.wzy.quanyoumall.search.controller")
public class EsExceptionAdvice {
    @ExceptionHandler(value = IOException.class)
    public R handlerIOE(IOException e) {
        e.printStackTrace();
        log.error(e.getMessage(), e.getStackTrace(), e.getClass());
        return R.error(bizCodeEnum.PRODUCT_UP_EXCEPTION.getCode(), bizCodeEnum.PRODUCT_UP_EXCEPTION.getMsg());
    }

    @ExceptionHandler(value = Throwable.class)
    public R HandlerOtherException(Exception e) {
        e.printStackTrace();
        log.error(e.getMessage(), e.getStackTrace(), e.getClass());
        return R.error(bizCodeEnum.UNKNOW_EXCEPTION.getCode(), bizCodeEnum.UNKNOW_EXCEPTION.getMsg());
    }
}
