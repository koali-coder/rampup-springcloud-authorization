package org.example.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Description
 * @Author zhouyw
 * @Date 2022/9/23 09:31
 **/
@RestController
@RequestMapping("/")
public class ClientController {

    @GetMapping("/client")
    public Map<String, Object> clientHello() {
        String value = "{\"result\":{\"bizCode\":200,\"resultCode\":\"SUCCESS\",\"message\":\"成功\",\"validResults\":[]},\"data\":{\"value\":\"hello client 3\"}}";
        return new Gson().fromJson(value, new TypeToken<Map<String, Object>>() {}.getType());
    }

}
