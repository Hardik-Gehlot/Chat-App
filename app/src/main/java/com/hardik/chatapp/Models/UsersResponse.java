package com.hardik.chatapp.Models;

import java.util.ArrayList;
import java.util.List;

public class UsersResponse {
    private int status;
    private String message;
    private ArrayList<User> data;

    public UsersResponse(int status, String message, ArrayList<User> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public UsersResponse() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<User> getData() {
        return data;
    }

    public void setData(ArrayList<User> data) {
        this.data = data;
    }
}
