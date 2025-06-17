package com.sky.exception;

/**
 * 账号不存在异常
 */
public class PhoneUnlegalException extends BaseException {

    public PhoneUnlegalException() {
    }

    public PhoneUnlegalException(String msg) {
        super(msg);
    }

}
