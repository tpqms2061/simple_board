package com.ssh.simple_board.repository;

import com.ssh.simple_board.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
//    find메소드는 id값을 기준으로 findbyid 이런거는 제공하는데 findbyUsername 은 우리가 따로 입력해야
//    username 에 맞게끔 메소드 제공  ==> 그리고 포맷은 findBy ( ) 이런식으로 만들어야 제공됨
    // optional : 유저가 있거나 없을수도있는데 유저네임 결과가 없을수도있기때문에
}
