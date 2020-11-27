package com.example.instock;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DBHelper {

    String name,username,email,password,profess,phNo;

    public DBHelper() {
    }

    public DBHelper(String name, String username, String email, String password, String profess, String phNo) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.profess = profess;
        this.phNo = phNo;
    }

    HashMap<String,DBHelper> users = new HashMap<>();

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getProfess() {
//        return profess;
//    }
//
//    public void setProfess(String profess) {
//        this.profess = profess;
//    }
//
//    public String getPhNo() {
//        return phNo;
//    }
//
//    public void setPhNo(String phNo) {
//        this.phNo = phNo;
//    }


}
