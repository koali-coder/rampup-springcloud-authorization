package org.example.handler;

import org.example.config.IgnoreUrlsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

/**
 * 白名单路径访问时需要移除JWT请求头
 * @Author zhouyw
 * @Date 2022/9/26 17:40
 **/
@Component
public class IgnoreUrlsRemoveJwtFilter implements WebFilter {

    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();
        PathMatcher pathMatcher = new AntPathMatcher();
        //白名单路径移除JWT请求头
        List<String> ignoreUrls = ignoreUrlsConfig.getUrls();
        if (!CollectionUtils.isEmpty(ignoreUrls)) {
            for (String ignoreUrl : ignoreUrls) {
                if (pathMatcher.match(ignoreUrl, uri.getPath()) && !uri.getPath().contains("/oauth2/token")) {
                    request = exchange.getRequest().mutate().header("Authorization", "").build();
                    exchange = exchange.mutate().request(request).build();
                    return chain.filter(exchange);
                }
            }
        }

        return chain.filter(exchange);
    }
}
