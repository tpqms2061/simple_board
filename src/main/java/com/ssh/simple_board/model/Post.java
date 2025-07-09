package com.ssh.simple_board.model;


import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt; //서비스를 만들때 거의 포함시킴

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    eager Post부를시 무조건user 를 가져옴 / Lazy 는 필요시에만 User 를 가져옴
    // optional =false : User가 반드시 존재해야됨 USer가 option이 되면 안됨
    @JoinColumn(name = "author_id")
//    table_id로 부를껀데 / 이걸 author_id로 부르도록 하는것
    private User author;


    //post 측에서 댓글 정보를 알아야되기때문에
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
//    mappedBy : Commnet필드에 지정한 필드명이 외래키를 관리한다
//    cascade = CascadeType.ALL : cascade : 나(post) => 내가 글을 삭제하면 연결된 모든 댓글들을 삭제
//    orphanRemoval = true : orphan = 관계(comment) => 댓글이 삭제되면 글에 있는 댓글들도 삭제된다.


    @OrderBy("createdAt ASC")
    private List<Comment> comments;
}
