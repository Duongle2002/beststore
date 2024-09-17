package com.example.lab5;

public class User {
    private String id;
    private String name;
    private String email;
    private String sclass;

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.sclass = sclass;

    }

    public String getSclass() {
        return sclass;
    }

    public void setSclass(String sclass) {
        this.sclass = sclass;
    }

    // Constructor mặc định (nếu không dùng Lombok)
    public User() {
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }
}

