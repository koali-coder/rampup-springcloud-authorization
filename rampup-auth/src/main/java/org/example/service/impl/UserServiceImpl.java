package org.example.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author zhouyw
 * @Date 2022/9/24 23:15
 **/
@RequiredArgsConstructor
@Slf4j
@Component
public class UserServiceImpl implements UserService {

//    @Autowired
//    UserMapper userMapper;

    Map<String, User> map = new HashMap<>();

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initData() {
        String password = passwordEncoder.encode("123456");
        map.put("admin", new User(1L,"admin", password,1, CollUtil.toList("ADMIN")));
        map.put("test", new User(2L,"test", password,1, CollUtil.toList("TEST")));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername,username));

        User mapUser = map.get(username);
        org.springframework.security.core.userdetails.User securityUser =
        new org.springframework.security.core.userdetails.User(mapUser.getUsername(), mapUser.getPassword(),
                mapUser.getAuthorities());
        if (!securityUser.isEnabled()) {
            throw new DisabledException("ACCOUNT_DISABLED");
        } else if (!securityUser.isAccountNonLocked()) {
            throw new LockedException("ACCOUNT_LOCKED");
        } else if (!securityUser.isAccountNonExpired()) {
            throw new AccountExpiredException("ACCOUNT_EXPIRED");
        } else if (!securityUser.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException("CREDENTIALS_EXPIRED");
        }

        return securityUser;
    }

}
