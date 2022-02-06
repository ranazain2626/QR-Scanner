package com.example.myapplication;

public class User {
    private String email,password,username;

    public User(String email, String password,String username){
        this.email=email;
        this.password=password;
        this.username=username;

    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username =username ;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


}
