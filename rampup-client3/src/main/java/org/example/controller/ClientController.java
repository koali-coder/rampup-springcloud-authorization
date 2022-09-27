package org.example.controller;

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

    @GetMapping("/client")
    public String clientHello() {
        return "hello client 3";
    }

}
