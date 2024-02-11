package com.scaffold.common.enums;

public enum ResponseCode {
    SUCCESS("000000","成功"),

    BUS_ERR("100000","业务异常"),
    BUS_VALIDATOR_ERR("100001","参数校验异常"),
    SYS_ERR("200000","系统异常"),

    DB_ERR("300000","数据库处理异常"),
    REDIS_ERR("400000","Redis处理异常"),

    FAILURE("999999","请求处理失败");

    private final String code;
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
