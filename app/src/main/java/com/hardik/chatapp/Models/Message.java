package com.hardik.chatapp.Models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    int mid;
    int sender;
    String message;
    long time;
    int status;

    public Message(int mid, int sender, String message, long time, int status) {
        this.mid = mid;
        this.sender = sender;
        this.message = message;
        this.time = time;
        this.status = status;
    }

    public Message(int mid, int sender, String message, long time) {
        this.mid = mid;
        this.sender = sender;
        this.message = message;
        this.time = time;
    }

    public Message() {
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
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
}
