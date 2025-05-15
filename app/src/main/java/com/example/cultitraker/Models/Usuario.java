package com.example.cultitraker.Models;

public class Usuario {
    private int id;
    private String email;
    private String password;
    public Usuario(){
        this.id=0;
        this.email ="";
        this.password ="";
    }

    public Usuario(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
