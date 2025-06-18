package com.sky.exception;

/**
 * 套餐启用失败异常
 */
public class SetmealNeedUniqueException extends BaseException {

    public SetmealNeedUniqueException(){}

    public SetmealNeedUniqueException(String msg){
        super(msg);
    }
}
