package com.aloj.whodefytest.ui.chat;

import com.aloj.whodefytest.model.Message;

import java.util.List;


public interface ChatView {
    void showMessages(List<Message> messages);

    void addMessage(Message message);

    void clearChat();

    void clearEditText();

    void showContactNoSelectedMessage();

    void showToast(String text);
}
