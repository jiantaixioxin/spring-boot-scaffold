package com.scaffold.manager;

import com.scaffold.common.conf.HelloConf;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

@Component
public class HelloManager {
    @Resource
    private HelloConf conf;

    public String hello() {

        return conf.getHello();
    }
}
