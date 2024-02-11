package com.scaffold.common.exceptions;

import com.scaffold.common.enums.ResponseCode;

public class BusinessException extends BaseException{

    public BusinessException(Throwable e) {
        super(e);
    }

    public BusinessException(String code, String message) {
        super(code, message);
    }

    public BusinessException(String code, String message, Object data) {
        super(code, message, data);
    }

    public BusinessException(String code, String message, Throwable e) {
        super(code, message, e);
    }

    public BusinessException(String code, String message, Object data, Throwable e) {
        super(code, message, data, e);
    }

    public static BusinessException businessException(String message){
        return new BusinessException(ResponseCode.BUS_ERR.getCode(),message);
    }
    public static BusinessException validationException(String message){
        return new BusinessException(ResponseCode.BUS_VALIDATOR_ERR.getCode(),message);
    }
}
