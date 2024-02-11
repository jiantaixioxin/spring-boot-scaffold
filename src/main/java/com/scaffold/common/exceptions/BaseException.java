package com.scaffold.common.exceptions;

/**
 * 基础异常
 */
public abstract class BaseException extends RuntimeException{
    /**
     * 异常码
     */
    private String code;
    /**
     * 异常数据，特殊场景，需携带异常数据时使用
     */
    private Object data;

    public BaseException(Throwable e){
        super(e);
    }

    public BaseException(String code,String message) {
        super(message);
        this.code = code;
    }

    public BaseException( String code, String message, Object data) {
        super(message);
        this.code = code;
        this.data = data;
    }
    public BaseException( String code, String message, Throwable e) {
        super(message,e);
        this.code = code;
    }

    public BaseException( String code,String message, Object data,Throwable e) {
        super(message,e);
        this.code = code;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
