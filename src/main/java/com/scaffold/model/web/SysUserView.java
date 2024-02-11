package com.scaffold.model.web;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class SysUserView {

    private String userId;

    private String userName;
    @Size(max = 11,message = "手机号长度不能超过11位")
    @NotBlank(message = "手机号不能为空")
    private String phoneNo;

    private String password;

}