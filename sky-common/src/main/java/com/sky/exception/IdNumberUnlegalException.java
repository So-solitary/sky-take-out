package com.sky.exception;

/**
 * 账号不存在异常
 */
public class IdNumberUnlegalException extends BaseException {

    public IdNumberUnlegalException() {
    }

    public IdNumberUnlegalException(String msg) {
        super(msg);
    }

}
