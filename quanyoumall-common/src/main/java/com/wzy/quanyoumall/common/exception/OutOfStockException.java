package com.wzy.quanyoumall.common.exception;

public class OutOfStockException extends RuntimeException {
    public OutOfStockException() {
        super("库存不足");
    }
}
