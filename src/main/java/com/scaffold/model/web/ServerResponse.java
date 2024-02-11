package com.scaffold.model.web;

import com.scaffold.common.enums.ResponseCode;
import com.scaffold.common.exceptions.BaseException;
import lombok.Data;
/*
* 全局响应报文
* */
@Data
public class ServerResponse {
    private String code;
    private String message;
    private Object data;

    public ServerResponse(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ServerResponse build(String code,String message,Object data){
        return new ServerResponse(code,message,data);
    }
    public static ServerResponse success(){
        return new ServerResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),null);
    }
    public static ServerResponse success(Object data){
        return new ServerResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),data);
    }
    public static ServerResponse failure(){
        return new ServerResponse(ResponseCode.FAILURE.getCode(), ResponseCode.FAILURE.getMessage(),null);
    }
    public static ServerResponse failure(String code,String message){
        return new ServerResponse(code,message,null);
    }
    public static ServerResponse failure(String code,String message,String data){
        return new ServerResponse(code,message,data);
    }
    public static ServerResponse failure(BaseException e){
        return new ServerResponse(e.getCode(),e.getMessage(),e.getData());
    }
}
