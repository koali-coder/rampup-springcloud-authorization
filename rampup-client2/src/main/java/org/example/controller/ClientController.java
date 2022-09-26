package org.example.controller;

import org.example.model.RestData;
import org.example.service.ClientApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author zhouyw
 * @Date 2022/9/23 09:31
 **/
@RestController
@RequestMapping("/")
public class ClientController {

    @Autowired
    ClientApiService apiService;

    @GetMapping("/client")
    public String clientHello() {
        return "hello client 2";
    }

    @GetMapping("/api")
    public RestData clientApi() {
        return RestData.success(apiService.helloServer());
    }

}
