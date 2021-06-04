package com.paas.idgen.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jaswine
 * @date 2021-06-02 11:57:16
 */
@Service
@Slf4j
public class TimeSerial {

    private final static String LUA_NO = "if redis.call('GET',KEYS[1]) == ARGV[1] then redis.call('SET', KEYS[1], 0) return 0 else return redis.call('INCR',KEYS[1]) end";
    private final DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(LUA_NO,Long.class);

    private final static String TIME_FORMAT = "yyyyMMddHHmmss";

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    public String generateSerial(){
        List<String> list = new ArrayList<>();
        list.add("TIME:SERIAL");
        Long no = redisTemplate.execute(redisScript, list, 99999L);
        String serial = DateTimeFormatter.ofPattern(TIME_FORMAT).format(LocalDateTime.now()).concat(String.format("%05d",no));
        log.info("the serial no is :{}",serial);
        return serial;
    }

}
