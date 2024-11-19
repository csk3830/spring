package com.ezen.spring.repository;

import com.ezen.spring.domain.AuthVO;
import com.ezen.spring.domain.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    int insert(UserVO userVO);

    int insertAuthInit(String email);

    UserVO selectEmail(String username);

    List<AuthVO> selectAuths(String username);

    List<UserVO> getList();

    int updateNoPwd(UserVO userVO);

    int update(UserVO userVO);

    int remove(String name);

    int removeAuth(String name);

    int lastUpdate(String name);
}
