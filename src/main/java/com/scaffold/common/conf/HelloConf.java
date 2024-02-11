package com.scaffold.common.conf;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class HelloConf {
    @Value("${config.hello}")
    private String hello;
}
