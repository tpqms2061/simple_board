package com.ssh.simple_board.controller;

import com.ssh.simple_board.dto.CommentDto;
import com.ssh.simple_board.dto.PostDto;
import com.ssh.simple_board.model.Comment;
import com.ssh.simple_board.model.Post;
import com.ssh.simple_board.model.User;
import com.ssh.simple_board.repository.CommentRepository;
import com.ssh.simple_board.repository.PostRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public String detail(
            @PathVariable Integer id,
            Model model,
            HttpSession httpSession
    ) {
        Post post = postRepository.findById(id).orElseThrow();
        //findAllById()는 jpaRepo 에서 기본적으로 제공
        model.addAttribute("post", post);
        model.addAttribute("commentDto", new CommentDto());

        return "post-detail";

    }

    //댓글 달기
    @PostMapping("/{postId}/comments")
    public String addComment(
            @PathVariable Integer postId, //comment Id인지 Post id인지 구별하기위해 명칭을 postid
            @Valid @ModelAttribute CommentDto commentDto,
            BindingResult bindingResult,
            HttpSession httpSession,
            Model model
    ) {
        Post post = postRepository.findById(postId).orElseThrow();

        if (bindingResult.hasErrors()) {
            model.addAttribute("post ", post);
            return "post-detail";
        }
        User user = currentUser(httpSession); //세션에서 유저정보 꺼내옴
        Comment comment = Comment.builder()
                .post(post)
                .author(user)
                .text(commentDto.getText())
                .createdAt(LocalDateTime.now())
                .build();
        commentRepository.save(comment);

        return "redirect:/posts/" + postId;
    }
}
