package com.example.restexample.model;

import com.example.restexample.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserModel {

    private Long id;
    private String nickname;
    private String name;
    private String email;
    private List<TaskModel> tasks;

    public UserModel() {
    }

    //Converting User entity to UserModel
    public static UserModel toModel(User user){

        UserModel userModel = new UserModel();
        userModel.setId(user.getId());
        userModel.setNickname(user.getNickname());
        userModel.setName(user.getName());
        userModel.setEmail(user.getEmail());
        userModel.setTasks(user.getTasks().stream().map(TaskModel::toModel).collect(Collectors.toList()));

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<TaskModel> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskModel> tasks) {
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
