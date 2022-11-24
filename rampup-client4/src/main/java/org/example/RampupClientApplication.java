package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zhouyw
 * <p>
 * 客户端服务
 */
@EnableDiscoveryClient
@SpringBootApplication
public class RampupClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(RampupClientApplication.class, args);
	}

}
