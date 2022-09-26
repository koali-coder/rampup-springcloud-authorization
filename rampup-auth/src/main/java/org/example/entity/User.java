package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Description
 * @Author zhouyw
 * @Date 2022/9/24 23:15
 **/
@Data
@AllArgsConstructor
public class User {

    private Long id;
    private String username;
    private String password;
    private Integer status;
    private List<String> roles;
    /**
     * 权限数据
     */
    private Collection<SimpleGrantedAuthority> authorities;

    public User(Long id, String username, String password, Integer status, List<String> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.status = status;
        this.roles = roles;

        if (this.getRoles() != null) {
            authorities = new ArrayList<>();
            this.getRoles().forEach(item -> authorities.add(new SimpleGrantedAuthority(item)));
        }
    }
}
