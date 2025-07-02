package com.coderscampus.domain;

import java.time.LocalDateTime;


public class Message {
    private Long id;
    private String content;
    private LocalDateTime timestamp;
    private Channel channel;
    private User user;


    public Message() {

    }

    public Message(Long id, String content, LocalDateTime timestamp, Channel channel, User user) {
        this.id = id;
        this.content = content;

        this.timestamp = timestamp;
        this.channel = channel;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                ", channel=" + channel +
                ", user=" + user +
                '}';
    }
}
