/*
package com.example.restexample.model;

import com.example.restexample.entity.User;

import java.util.List;
import java.util.stream.Collectors;

//Model of User entity w/o email for user requests
public class UserModelUser {

    private Long id;
    private String nickname;
    private String name;
    private List<TaskModel> tasks;

    public UserModelUser() {
    }

    //Converting User entity to UserModel
    public static UserModelUser toModel(User user){

        UserModelUser userModelUser = new UserModelUser();
        userModelUser.setId(user.getId());
        userModelUser.setNickname(user.getNickname());
        userModelUser.setName(user.getName());
        userModelUser.setTasks(user.getTasks().stream().map(TaskModel::toModel).collect(Collectors.toList()));

        return userModelUser;
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
*/
