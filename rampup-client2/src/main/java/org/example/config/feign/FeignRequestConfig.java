package org.example.config.feign;

import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 为解决调用feign接口，需要认证服务
 * 因此实现RequestInterceptor，对feign接口进行拦截，使其request中的header头部添加上token参数
 * @author zhouyw
 */
@ConditionalOnClass(Feign.class)
@Slf4j
@Configuration
public class FeignRequestConfig implements RequestInterceptor {

    public static final String T_REQUEST_ID = "zxg";

    /**
     * 注册自定义复写的Feign异常解析器
     */
    @Bean
    public ErrorDecoder errorDecoder(){
        return new OpenFeignErrorDecoder();
    }

    @Override
    public void apply(RequestTemplate template) {
        HttpServletRequest httpServletRequest =   getHttpServletRequest();
        if(httpServletRequest!=null){
            Map<String, String> headers = getHeaders(httpServletRequest);
            // 传递所有请求头,防止部分丢失
            //此处也可以只传递认证的header
            //requestTemplate.header("Authorization", request.getHeader("Authorization"));
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                template.header(entry.getKey(), entry.getValue());
            }
            // 微服务之间传递的唯一标识,区分大小写所以通过httpServletRequest获取
            if (httpServletRequest.getHeader(T_REQUEST_ID)==null) {
                String sid = String.valueOf(UUID.randomUUID());
                template.header(T_REQUEST_ID, sid);
            }
            log.debug("FeignRequestInterceptor:{}", template.toString());
        }
    }


    private HttpServletRequest getHttpServletRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取原请求头
     */
    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        if(enumeration!=null){
            while (enumeration.hasMoreElements()) {
                String key = enumeration.nextElement();
                String value = request.getHeader(key);
                map.put(key, value);
            }
        }
        return map;
    }

}
