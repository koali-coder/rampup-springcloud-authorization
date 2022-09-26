package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername,username));
        return new org.springframework.security.core.userdetails.User("admin", "123456", new ArrayList<>());
    }

}
