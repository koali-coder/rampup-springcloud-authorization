package org.example.service;

import org.example.config.feign.FeignRequestConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "rampup-client", configuration = FeignRequestConfig.class)
public interface ClientApiService {

    @GetMapping("/client")
    String helloServer();

}
