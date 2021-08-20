package com.hardik.chatapp.Models;

public class User {
    private int id;
    private String fname;
    private String lname;
    private String email;
    private String username;
    private String profile_img;
    private long lastSeen;
    private String message;
    private int sender;
    private int status;

    public User(int id, String fname, String lname, String email, String username, String profile_img, long lastSeen, String message, int sender, int status) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.username = username;
        this.profile_img = profile_img;
        this.lastSeen = lastSeen;
        this.message = message;
        this.sender = sender;
        this.status = status;
    }

    public User(int id, String fname, String lname, String email, String profile_img) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.profile_img = profile_img;
    }

    public User(int id, String fname, String lname, String email, String profile_img, long lastSeen) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.profile_img = profile_img;
        this.lastSeen = lastSeen;
    }

    public User(String fname, String lname, String email, String username, String profile_img) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.username = username;
        this.profile_img = profile_img;
    }

    public long getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(long lastSeen) {
        this.lastSeen = lastSeen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile_img() {
        return profile_img;
    }

    public void setProfile_img(String profile_img) {
        this.profile_img = profile_img;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
