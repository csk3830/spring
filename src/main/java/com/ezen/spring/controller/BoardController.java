package com.ezen.spring.controller;

import com.ezen.spring.domain.BoardVO;
import com.ezen.spring.domain.PagingVO;
import com.ezen.spring.handler.PagingHandler;
import com.ezen.spring.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequestMapping("/board/*")
@RequiredArgsConstructor
@Slf4j
@Controller
public class BoardController {
    private final BoardService bsv;

    @GetMapping("/register")
    public String register() {
        return "/board/register";
    }

    @PostMapping("/register")
    public String register(BoardVO boardVO) {
        log.info(">>>> boardVO >> {}", boardVO);
        int isOk = bsv.register(boardVO);
        return "redirect:/board/list";
    }

    @GetMapping("/list")
    public String list(Model model, PagingVO pgvo) {
        // 전체 게시글 수 가져오기
        int totalCount = bsv.getTotalCount(pgvo);
        PagingHandler ph = new PagingHandler(pgvo, totalCount);
        model.addAttribute("list", bsv.getList(pgvo));
        model.addAttribute("ph", ph);

        return "/board/list";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam("bno") long bno, Model model) {
        BoardVO bvo = bsv.getDetail(bno);
        model.addAttribute("bvo", bvo);
        return "/board/detail";
    }

    @PostMapping("/modify")
    public String modify(BoardVO bvo, RedirectAttributes redirectAttributes) {
        log.info(">>>> bvo >> {}", bvo);
        int isOk = bsv.update(bvo);
        redirectAttributes.addAttribute("bno", bvo.getBno());
        return "redirect:/board/detail";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("bno") long bno) {
        int isOk = bsv.delete(bno);
        log.info(">>>> delete > {}", isOk>0? "OK":"Fail");
        return "redirect:/board/list";
    }
}
