package com.scaffold.service.redis;

import com.scaffold.rpc.client.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

@Component
public class RedisService {
    @Resource
    private RedisClient redisClient;
    public String getToken() {
        String key= UUID.randomUUID().toString();
        redisClient.set(key,"123",60);
        return key;
    }

    public String getTokenInfo(String key) {
        String data=redisClient.get(key).toString();
        return data;
    }
}
