package com.ssh.simple_board.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity // 데이터베이스에 반영하는 거구나 라고 인식함
@Table(name="users") //테이블 \dt 생성
public class User {

    @Id //기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) //AUTO_INCREMENT 기능: 숫자형 컬럼의 값을 자동으로 증가시키는 기능
    // 엔티티의 기본 키(@Id) 값을 자동 생성하도록 설정할 때 사용하는 어노테이션
    private Integer id;  //primary key 이면서도  다른 데이터베이스에서 참조할수있는 외래키 역할

    @Column(nullable = false, unique = true, length =50) //nullable : 빈값이 들어가도되는지  / unique: 중복가능여부
    private String username;

    @Column(nullable = false)
    private String password; //




}
