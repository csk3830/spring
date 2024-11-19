package com.ezen.spring.security;

import com.ezen.spring.domain.UserVO;
import com.ezen.spring.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;



public class CustomUserService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // userName 주고 UserVO 객체 리턴 (authList도 같이)
        UserVO userVO = userMapper.selectEmail(username);
        userVO.setAuthList(userMapper.selectAuths(username));
        //UserDetails return
        return new AuthUser(userVO);
    }
}
