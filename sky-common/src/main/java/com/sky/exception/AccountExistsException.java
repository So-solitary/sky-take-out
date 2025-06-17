package com.sky.exception;

/**
 * 账号不存在异常
 */
public class AccountExistsException extends BaseException {

    public AccountExistsException() {
    }

    public AccountExistsException(String msg) {
        super(msg);
    }

}
