package com.scaffold.common.exceptions;

import com.scaffold.common.enums.ResponseCode;

public class SystemException extends BaseException{

    public SystemException(Throwable e) {
        super(e);
    }

    public SystemException(String code, String message) {
        super(code, message);
    }

    public SystemException(String code, String message, Object data) {
        super(code, message, data);
    }

    public SystemException(String code, String message, Throwable e) {
        super(code, message, e);
    }

    public SystemException(String code, String message, Object data, Throwable e) {
        super(code, message, data, e);
    }
    public static SystemException systemException(String message){
        return new SystemException(ResponseCode.SYS_ERR.getCode(),message);
    }
    public static SystemException dbException(String message){
        return new SystemException(ResponseCode.DB_ERR.getCode(),message);
    }
    public static SystemException redisException(String message){
        return new SystemException(ResponseCode.DB_ERR.getCode(),message);
    }
}
