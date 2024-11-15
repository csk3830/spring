package com.ezen.spring.controller;

import com.ezen.spring.domain.BoardDTO;
import com.ezen.spring.domain.BoardVO;
import com.ezen.spring.domain.FileVO;
import com.ezen.spring.domain.PagingVO;
import com.ezen.spring.handler.FileHandler;
import com.ezen.spring.handler.FileRemoveHandler;
import com.ezen.spring.handler.PagingHandler;
import com.ezen.spring.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/board/*")
@RequiredArgsConstructor
@Slf4j
@Controller
public class BoardController {
    private final BoardService bsv;
    private final FileHandler fh;

    @GetMapping("/register")
    public String register() {
        return "/board/register";
    }

    @PostMapping("/register")
    public String register(BoardVO boardVO, @RequestParam(name="files", required = false) MultipartFile[] files) {
        log.info(">>>> boardVO >> {}", boardVO);
        List<FileVO> flist = null;
        if(files[0].getSize() > 0 && files != null){
            flist = fh.uploadFile(files);
            log.info(">>>> flist >> {}", flist);
        }
        int isOk = bsv.register(new BoardDTO(boardVO, flist));
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
        model.addAttribute("bdto", bsv.getDetail(bno));
        return "/board/detail";
    }

    @PostMapping("/modify")
    public String modify(BoardVO bvo, RedirectAttributes redirectAttributes, @RequestParam(value = "files", required = false)MultipartFile[] files) {
        List<FileVO> flist = null;
        if(files != null && files[0].getSize()>0){
            flist = fh.uploadFile(files);
        }
        int isOk = bsv.update(new BoardDTO(bvo, flist));
        redirectAttributes.addAttribute("bno", bvo.getBno());
        return "redirect:/board/detail";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("bno") long bno) {
        int isOk = bsv.delete(bno);
        log.info(">>>> delete > {}", isOk>0? "OK":"Fail");
        return "redirect:/board/list";
    }

    @ResponseBody
    @DeleteMapping("/file/{uuid}")
    public String removeFile(@PathVariable("uuid") String uuid) {
        FileVO fvo = bsv.getFile(uuid);
        int isOk = bsv.removeFile(uuid);
        //파일 삭제
        FileRemoveHandler fr = new FileRemoveHandler();
        boolean isDel = fr.deleteFile(fvo);
        return (isOk > 0 && isDel) ? "1" : "0";
    }

}
