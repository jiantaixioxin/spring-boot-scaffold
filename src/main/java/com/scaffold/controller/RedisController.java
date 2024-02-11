package com.scaffold.controller;

import com.scaffold.manager.SysUserManager;
import com.scaffold.model.db.SysUser;
import com.scaffold.model.web.ServerResponse;
import com.scaffold.service.redis.RedisService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/rpc")
public class RedisController {
    @Resource
    private RedisService redisService;

    @RequestMapping("/getToken")
    public ServerResponse getToken() {
        String token=redisService.getToken();
        return ServerResponse.success(token);
    }
    @RequestMapping("/getTokenInfo/{key}")
    public ServerResponse getTokenInfo(@PathVariable String key) {
        String data=redisService.getTokenInfo(key);
        return ServerResponse.success(data);
    }
}
