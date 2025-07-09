package com.ssh.simple_board.controller;

import com.ssh.simple_board.dto.PostDto;
import com.ssh.simple_board.model.Post;
import com.ssh.simple_board.model.User;
import com.ssh.simple_board.repository.CommentRepository;
import com.ssh.simple_board.repository.PostRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostsController {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private User currentUser(HttpSession httpSession) {  //httpSession 을 사용해서 현재 세션에 들어있는 유저를 확인
        return (User) httpSession.getAttribute("user");
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("posts", postRepository.findAll());
        return "post-list";
    }

    @GetMapping("/add")
    public String addForm(Model model, HttpSession httpSession) {
        if (currentUser(httpSession) == null) return "redirect:/login";
//        httpsession = null이라면 만료되거나 없는 유저 로그인 페이지로

        model.addAttribute("postDto", new PostDto());// 유저정보가 있다면  글 쓰기

        return "post-form";
    }

    @PostMapping("/add")
    public String add(
            @Valid @ModelAttribute PostDto postDto,
            BindingResult bindingResult,
            HttpSession httpSession
    ) {
        if (bindingResult.hasErrors()) return "post-form";

        User user = currentUser(httpSession);
        Post post = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .author(user)
                .createdAt(LocalDateTime.now())
                .build();

        postRepository.save(post);
        return "redirect:/posts";
    }
}
