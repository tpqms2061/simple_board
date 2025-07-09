package com.ssh.simple_board.controller;

import com.ssh.simple_board.dto.LoginDto;
import com.ssh.simple_board.model.User;
import com.ssh.simple_board.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserRepository userRepository;


    @GetMapping({"/" , "/login"})
    public String loginForm(Model model) {
        model.addAttribute("loginDto", new LoginDto());

        return "login";
    }

    @PostMapping("/login")
    public String login(
            @Valid @ModelAttribute LoginDto loginDto,
            BindingResult bindingResult,
            HttpSession httpSession, //로그인 성공시 HttpSession 을 달아줘야됨
            Model model
    ) {
        if(bindingResult.hasErrors()) return "login";

        User user = userRepository.findByUsername(loginDto.getUsername()).orElse(null);

        if (user == null || !user.getPassword().equals(loginDto.getPassword())) {
            model.addAttribute("error" , "아이디 /비밀번호가 올바르지 않습니다.");

//            optional이기때문에 null일수도 있고 로그인 비밀번호가 DTO 비밀번호와 올바르지 않을떄 에러발생
            return "login";
        }

        httpSession.setAttribute("user", user); //유저정보를 담는다
        return "redirect:/posts";
    }

}
