package com.scaffold.controller;

import com.scaffold.manager.HelloManager;
import com.scaffold.model.web.ServerResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/scaffold")
public class HelloController {

    @Resource
    private HelloManager helloManager;

    @RequestMapping("/hello")
    public ServerResponse hello() {
        String data=helloManager.hello();
        return ServerResponse.success(data);
    }
}
