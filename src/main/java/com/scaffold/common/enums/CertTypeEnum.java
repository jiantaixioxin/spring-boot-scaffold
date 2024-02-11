package com.scaffold.common.enums;
/*
* 枚举类：证件类型
* */
public enum CertTypeEnum {
    ID_CARD("01","身份证"),
    DRIVER_LICENSE("02","驾驶证");
    private final String code;
    private final String name;
    CertTypeEnum(String code,String name){
        this.code=code;
        this.name=name;
    }
    public static CertTypeEnum getByCode(String code){
        CertTypeEnum[] values=CertTypeEnum.values();
        for (CertTypeEnum value:values){
            if(value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }
    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
