package br.com.reips.chatroom.model;

import java.time.LocalDateTime;

public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;
    private String receiver;
    private LocalDateTime dateTime = LocalDateTime.now();

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE,
        TYPING
    }

}
