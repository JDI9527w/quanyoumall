package com.wzy.quanyoumall.common.exception;

public class EmailExistException extends  RuntimeException{
    public EmailExistException() {
        super("该邮箱已注册");
    }
}
