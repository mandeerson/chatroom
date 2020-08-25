package br.com.reips.chatroom.model;

import java.util.ArrayList;
import java.util.List;

public class Content {

    private List<Activity> activity = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private String content = "";

    public List<Activity> getActivity() {
        return activity;
    }

    public void setActivity(List<Activity> activity) {
        this.activity = activity;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
