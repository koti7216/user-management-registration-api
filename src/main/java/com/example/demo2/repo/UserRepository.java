package com.example.demo2.repo;

import com.example.demo2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    List<User> findByUnameAndUpassword(String uname,String upassword);
    List<User> findByUname(String uname);
}
