package org.example.config;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPublicKey;

/**
 * @Description
 * @Author zhouyw
 * @Date 2022/9/25 20:51
 **/
@EnableWebSecurity(debug = true)
public class DefaultSecurityConfig {
    /**
     * 配置 请求授权
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        // 配置 请求授权
        http.authorizeRequests(authorizeRequests ->
                        // 任何请求都需要认证（不对未登录用户开放）
                        authorizeRequests.anyRequest().authenticated()
                )
                // 表单登录
                .formLogin()
                .and()
                .logout();
//                .and()
//                .oauth2ResourceServer().jwt();
        return http.build();
    }

//    /**
//     * 模拟用户
//     *
//     * @return
//     */
//    @Bean
//    UserDetailsService users() {
//        UserDetails user = User.builder()
//                .username("admin")
//                .password("123456")
//                .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode)
//                .roles("USER")
//                //.authorities("SCOPE_userinfo")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }

//    /**
//     * jwt解码器
//     * 客户端认证授权后，需要访问user信息，解码器可以从令牌中解析出user信息
//     *
//     * @return
//     */
//    @SneakyThrows
//    @Bean
//    JwtDecoder jwtDecoder() {
//        CertificateFactory certificateFactory = CertificateFactory.getInstance("x.509");
//        // 读取cer公钥证书来配置解码器
//        ClassPathResource resource = new ClassPathResource("myjks.cer");
//        Certificate certificate = certificateFactory.generateCertificate(resource.getInputStream());
//        RSAPublicKey publicKey = (RSAPublicKey) certificate.getPublicKey();
//        return NimbusJwtDecoder.withPublicKey(publicKey).build();
//    }

    /**
     * 开放一些端点的访问控制
     * 不需要认证就可以访问的端口
     * @return
     */
    //@Bean
/*    WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers("/actuator/health", "/actuator/info");
    }*/
}

