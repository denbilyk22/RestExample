package com.example.restexample.model;

import com.example.restexample.entity.Task;
import com.example.restexample.entity.User;

import java.util.List;

//Model of User entity w/o email for user requests
public class UserModel {

    private Long id;
    private String nickname;
    private String name;
    private List<Task> tasks;

    public UserModel() {
    }

    //Converting User entity to UserModel
    public static UserModel toModel(User user){

        UserModel userModel = new UserModel();
        userModel.setId(user.getId());
        userModel.setNickname(user.getNickname());
        userModel.setName(user.getName());
        userModel.setTasks(user.getTasks());

        return userModel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", name='" + name + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
