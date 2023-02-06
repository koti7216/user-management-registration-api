package com.example.demo2.service;

import com.example.demo2.entity.SignUser;
import com.example.demo2.entity.User;
import com.example.demo2.entity.UserName;
import com.example.demo2.repo.UserRepository;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository repo;

    Argon2 argon2= Argon2Factory.create();
    public void postUser(User user) {
         String password=argon2.hash(2,65586,1, user.getUpassword());
         user.setUpassword(password);
        repo.save(user);
    }

    public List<User> getUser() {
        return repo.findAll();
    }

    public User getUserById(Long id) {
        return repo.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<User> getByName(SignUser user) {
        String uname= user.getUname();
        if(repo.findByUname(uname).size()==0){
            return repo.findByUname(uname);
        }
        else {

            if (argon2.verify(repo.findByUname(uname).get(0).getUpassword(), user.getUpassword()))
                return repo.findByUname(uname);
            else{
                return Collections.emptyList();
            }
        }
    }



    public void delete(Long id) {
        repo.deleteById(id);
    }

    public List<User> getName(UserName uname) {
        String name=uname.getUname();
        return repo.findByUname(name);
    }
}
