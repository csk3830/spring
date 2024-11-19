package com.ezen.spring.controller;

import com.ezen.spring.domain.UserVO;
import com.ezen.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Slf4j
@RequestMapping("/user/*")
@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService usv;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String signup() {
        return "/user/register";
    }

    @PostMapping("/signup")
    public String signup(UserVO userVO){
        userVO.setPwd(passwordEncoder.encode(userVO.getPwd()));
        int isOk = usv.insert(userVO);
        return "/index";
    }

    @GetMapping("/login")
    public String login() {
        return "/user/login";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<UserVO> userList = usv.getList();
        model.addAttribute("userList", userList);
        return "/user/list";
    }

    @GetMapping("/modify")
    public void modify(){

    }

    @PostMapping("/modify")
    public String modify(UserVO userVO){
        log.info(">>>> userVO >> {}", userVO);
        int isOk = 0;
        if( userVO.getPwd() == null || userVO.getPwd().isEmpty()){
            isOk = usv.modifyNoPwd(userVO);
        }else{
            userVO.setPwd(passwordEncoder.encode(userVO.getPwd()));
            isOk = usv.modify(userVO);
        }
        return "redirect:/user/logout";
    }

    @GetMapping("/remove")
    public String remove(Principal principal){
        log.info(">>> principal >> {}", principal);
        int isOk = usv.remove(principal.getName());
        return "redirect:/user/logout";
    }

    // 로그아웃하면, last login update
    @GetMapping("/last")
    public String lastUpdate(Principal principal){
        int isOk = usv.lastUpdate(principal.getName());
        return "redirect:/user/logout";
    }
}