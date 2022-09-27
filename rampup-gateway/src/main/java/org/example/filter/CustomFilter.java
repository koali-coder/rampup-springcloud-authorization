//package org.example.filter;
//
//import cn.hutool.core.collection.CollUtil;
//import com.alibaba.cloud.commons.lang.StringUtils;
//import com.nimbusds.jose.JWSObject;
//import com.nimbusds.jose.shaded.json.JSONObject;
//import lombok.extern.slf4j.Slf4j;
//import org.example.config.IgnoreUrlsConfig;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.util.CollectionUtils;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import javax.annotation.PostConstruct;
//import java.nio.charset.StandardCharsets;
//import java.text.ParseException;
//import java.util.List;
//import java.util.Map;
//import java.util.TreeMap;
//
///**
// * @Description
// * @Author zhouyw
// * @Date 2022/9/26 17:40
// **/
//@Slf4j
//@Component
//public class CustomFilter implements GlobalFilter, Ordered {
//
////    private Map<String, List<String>> resourceRolesMap;
////
////    @PostConstruct
////    private void initRole() {
////        resourceRolesMap = new TreeMap<>();
////        resourceRolesMap.put("/client3/client", CollUtil.toList("ADMIN"));
////        resourceRolesMap.put("/client2/client", CollUtil.toList("ADMIN", "TEST"));
////    }
//
//    @Autowired
//    IgnoreUrlsConfig ignoreUrlsConfig;
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        String requestUrl = exchange.getRequest().getPath().value();
//        AntPathMatcher pathMatcher = new AntPathMatcher();
//        //1 auth服务所有放行
//        List<String> ignoreUrls = ignoreUrlsConfig.getUrls();
//        if (!CollectionUtils.isEmpty(ignoreUrls)) {
//            for (String ignoreUrl : ignoreUrls) {
//                if (pathMatcher.match(ignoreUrl, requestUrl)) {
//                    return chain.filter(exchange);
//                }
//            }
//        }
//        //2 检查token是否存在
//        String token = getToken(exchange);
//        if (StringUtils.isBlank(token)) {
//            return noTokenMono(exchange);
//        }
//        //3 判断是否是有效的token
//        try {
//            //从token中解析用户信息并设置到Header中去
//            String realToken = token.replace("Bearer ", "");
//            JWSObject jwsObject = JWSObject.parse(realToken);
//            String userStr = jwsObject.getPayload().toString();
//            log.info("AuthGlobalFilter.filter() user:{}",userStr);
//            ServerHttpRequest request = exchange.getRequest().mutate().header("user", userStr).build();
//            exchange = exchange.mutate().request(request).build();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return chain.filter(exchange);
//
//    }
//
//    /**
//     * 获取token
//     */
//    private String getToken(ServerWebExchange exchange) {
//        String tokenStr = exchange.getRequest().getHeaders().getFirst("Authorization");
//        if (StringUtils.isBlank(tokenStr)) {
//            return null;
//        }
//        String token = tokenStr.split(" ")[1];
//        if (StringUtils.isBlank(token)) {
//            return null;
//        }
//        return token;
//    }
//
//    /**
//     * 无效的token
//     */
//    private Mono<Void> invalidTokenMono(ServerWebExchange exchange) {
//        JSONObject json = new JSONObject();
//        json.put("status", HttpStatus.UNAUTHORIZED.value());
//        json.put("data", "无效的token");
//        return buildReturnMono(json, exchange);
//    }
//
//    private Mono<Void> noTokenMono(ServerWebExchange exchange) {
//        JSONObject json = new JSONObject();
//        json.put("status", HttpStatus.UNAUTHORIZED.value());
//        json.put("data", "没有token");
//        return buildReturnMono(json, exchange);
//    }
//
//
//    private Mono<Void> buildReturnMono(JSONObject json, ServerWebExchange exchange) {
//        ServerHttpResponse response = exchange.getResponse();
//        byte[] bits = json.toJSONString().getBytes(StandardCharsets.UTF_8);
//        DataBuffer buffer = response.bufferFactory().wrap(bits);
//        response.setStatusCode(HttpStatus.UNAUTHORIZED);
//        //指定编码，否则在浏览器中会中文乱码
//        response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
//        return response.writeWith(Mono.just(buffer));
//    }
//
//
//    @Override
//    public int getOrder() {
//        return 0;
//    }
//
//}
