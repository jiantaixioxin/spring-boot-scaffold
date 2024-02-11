package com.scaffold.controller;

import com.scaffold.manager.SysUserManager;
import com.scaffold.model.db.SysUser;
import com.scaffold.model.web.ServerResponse;
import com.scaffold.model.web.SysUserView;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/sys/user")
public class SysUserController {
    @Resource
    private SysUserManager sysUserManager;

    @RequestMapping("/query/{userId}")
    public ServerResponse query(@PathVariable String userId) {
        SysUser data=sysUserManager.query(userId);
        return ServerResponse.success(data);
    }

    @RequestMapping(value = "/add",method= RequestMethod.POST,produces = "application/json",consumes = "application/json")
    public ServerResponse add(@Valid @RequestBody SysUserView userView) {
        String userId=sysUserManager.add(userView);
        return ServerResponse.success(userId);
    }

}
