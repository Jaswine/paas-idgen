package com.paas.id.rest;

import com.paas.idgen.service.TimeSerial;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Jaswine
 * @date 2021-06-02 12:08:14
 */
@RestController
@RequestMapping(value = "id")
public class TimeSerialRest {

    @Resource
    private TimeSerial timeSerial;

    @GetMapping
    public String getId(){
        return timeSerial.generateSerial();
    }
}
