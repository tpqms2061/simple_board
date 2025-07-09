package com.ssh.simple_board.repository;

import com.ssh.simple_board.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

//JPA 는 interface로 설정해야 CRUD를 사용가능함
public interface CommentRepository extends JpaRepository<Comment, Integer> {
//<구현하고자 하는 레포지토리의 모델명 , 기본키의 타입>
}
