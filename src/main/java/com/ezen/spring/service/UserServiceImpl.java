package com.ezen.spring.service;

import com.ezen.spring.domain.UserVO;
import com.ezen.spring.repository.CommentMapper;
import com.ezen.spring.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    @Transactional
    @Override
    public int insert(UserVO userVO) {
        int isOk = userMapper.insert(userVO);
        if(isOk > 0){
            userMapper.insertAuthInit(userVO.getEmail());
        }
        return isOk;
    }
}
