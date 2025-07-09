package com.ssh.simple_board.controller;

import com.ssh.simple_board.dto.LoginDto;
import com.ssh.simple_board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserRepository userRepository;


    @GetMapping({"/" , "/login"})
    public String loginForm(Model model) {
        model.addAttribute("loginDto", new LoginDto());

        return "login";
    }

}
