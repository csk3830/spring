package com.ezen.spring.controller;

import com.ezen.spring.domain.CommentVO;
import com.ezen.spring.domain.PagingVO;
import com.ezen.spring.handler.PagingHandler;
import com.ezen.spring.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/comment/*")
@RestController// 일반 컨트롤러랑 별반 상관 없다. 일반 컨트롤러로 해도 이상없지만 비동기는 RestController로 하자는게 규칙.
public class CommentController {
    private final CommentService csv;

    @PostMapping("/post")
    @ResponseBody
    public ResponseEntity<String> post(@RequestBody CommentVO commentVO){
        log.info(">>>>>>> post commentVO {}", commentVO);
        int isOk = csv.post(commentVO);
        return isOk > 0?
                new ResponseEntity<String>("1", HttpStatus.OK) :
                new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR); //error시
    }

    @GetMapping("/list/{bno}/{page}")
    @ResponseBody
    public PagingHandler list(@PathVariable("bno")long bno, @PathVariable("page")int page){
        PagingVO pgvo = new PagingVO(page, 5);
        PagingHandler ph = csv.getList(bno, pgvo);
        log.info(">>> ph >> {}", ph);
        return ph;
    }

    @ResponseBody
    @DeleteMapping("/remove/{cno}")
    public String delete(@PathVariable("cno") long cno){
        int isOk = csv.remove(cno);
        return isOk > 0? "1" : "0";
    }

    @PutMapping("/update")
    @ResponseBody
    public String update(@RequestBody CommentVO commentVO){
        int isOk = csv.update(commentVO);
        return isOk > 0? "1" : "0";
    }


}
