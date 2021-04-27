package com.example.restexample.model;

import com.example.restexample.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor @Getter @Setter
public class UserModel {

    private Long id;
    private String nickname;
    private String name;
    private String email;
    private List<TaskModel> tasks;

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
}
