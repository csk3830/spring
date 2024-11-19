package com.ezen.spring.service;

import com.ezen.spring.domain.UserVO;
import com.ezen.spring.repository.CommentMapper;
import com.ezen.spring.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public List<UserVO> getList(){
        List<UserVO> userList = userMapper.getList();
        for(UserVO userVO : userList){
            userVO.setAuthList(userMapper.selectAuths(userVO.getEmail()));
        }
        log.info(">>>> userList >> {}", userList);
        return userList;
    }

    @Override
    public int modifyNoPwd(UserVO userVO) {
        return userMapper.updateNoPwd(userVO);
    }

    @Override
    public int modify(UserVO userVO) {
        return userMapper.update(userVO);
    }

    @Override
    public int remove(String name) {
        int isOk = userMapper.removeAuth(name);
        if(isOk > 0){
            userMapper.remove(name);
        }
        return isOk;
    }

    @Override
    public int lastUpdate(String name) {
        return userMapper.lastUpdate(name);
    }


}
