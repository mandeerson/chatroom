package br.com.reips.chatroom.model;

import java.time.LocalDateTime;

import br.com.reips.chatroom.enums.ActivityType;

public class Activity {

    private User user;
    private ActivityType type;
    private LocalDateTime date = LocalDateTime.now();

    public Activity(User user, ActivityType type) {
        this.user = user;
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ActivityType getType() {
        return type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

}
