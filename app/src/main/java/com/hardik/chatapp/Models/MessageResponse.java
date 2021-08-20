package com.hardik.chatapp.Models;

import java.util.ArrayList;

public class MessageResponse {
    private int status;
    private String message;
    private ArrayList<Message> data;

    public MessageResponse(int status, String message, ArrayList<Message> data) {
        this.status = status;
        this.message = message;
        this.data = data;
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

    public ArrayList<Message> getData() {
        return data;
    }

    public void setData(ArrayList<Message> data) {
        this.data = data;
    }
}
