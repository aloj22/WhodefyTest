package com.aloj.whodefytest.model;

public class Message {

    private String text;
    private String receiverName;
    private String senderName;
    private long time;

    public Message() {
        text = "";
        receiverName = "";
        senderName = "";
        time = 0;
    }

    public Message(String text, String receiverName, long time, String senderName) {
        this.text = text;
        this.receiverName = receiverName;
        this.time = time;
        this.senderName = senderName;
    }

    public String getText() {
        return text;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public long getTime() {
        return time;
    }

    public String getSenderName() {
        return senderName;
    }
}
