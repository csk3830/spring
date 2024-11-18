package com.ezen.spring.service;

import com.ezen.spring.domain.CommentVO;
import com.ezen.spring.domain.PagingVO;
import com.ezen.spring.handler.PagingHandler;

public interface CommentService {
    int post(CommentVO commentVO);

    PagingHandler getList(long bno, PagingVO pgvo);

    int remove(long cno);

    int update(CommentVO commentVO);
}
