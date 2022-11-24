package org.example.handler;

import cn.hutool.core.collection.CollUtil;
import com.google.gson.Gson;
import org.example.constant.AuthConstant;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * 鉴权管理器，用于判断是否有资源的访问权限
 * @Author zhouyw
 * @Date 2022/9/26 17:40
 **/
@Component
public class AuthorizationHandler implements ReactiveAuthorizationManager<AuthorizationContext> {

    private Map<String, List<String>> resourceRolesMap;

    @PostConstruct
    private void initRole() {
        resourceRolesMap = new TreeMap<>();
        resourceRolesMap.put("/client3/client", CollUtil.toList("ADMIN"));
        resourceRolesMap.put("/client/client", CollUtil.toList("ADMIN", "TEST"));
        resourceRolesMap.put("/auth/logout", CollUtil.toList("ADMIN"));
    }

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        //从Redis中获取当前路径可访问角色列表
        URI uri = authorizationContext.getExchange().getRequest().getURI();

        // todo db
        List<String> authorities = resourceRolesMap.get(uri.getPath());

        authorities = authorities.stream().map(i -> i = AuthConstant.AUTHORITY_PREFIX + i).collect(Collectors.toList());
        //认证通过且角色匹配的用户可访问当前路径
        return mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authorities::contains)
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

}
