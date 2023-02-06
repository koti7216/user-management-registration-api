package com.example.demo2.entity;


import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "user3")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="uid")
    private Long uid;


    @Column(name="uname")
    private String uname;
    @Column(name="upassword")
    private String upassword;
    @Column(name="scode")
    private Long scode;

    public Long getuId() {
        return uid;
    }

    public String getUpassword() {
        return upassword;
    }

    public Long getScode() {
        return scode;
    }

    public String getUname() {
        return uname;
    }

    public void setId(Long id) {
        this.uid = id;
    }

    public void setUpassword(String user_password) {
        this.upassword = user_password;
    }

    public void setScode(Long secret_code) {
        this.scode = secret_code;
    }

    public void setUname(String user_name) {
        this.uname = user_name;
    }
}
