package com.example.restexample.model;

import com.example.restexample.entity.Task;

public class TaskModel {

    private Long id;
    private String description;
    private Boolean completed;
    private String userNickname;

    public TaskModel() {
    }

    public static TaskModel toModel(Task task){
        TaskModel taskModel = new TaskModel();
        taskModel.setId(task.getId());
        taskModel.setDescription(task.getDescription());
        taskModel.setCompleted(task.getCompleted());
        taskModel.setUserNickname(task.getUser().getNickname());
        return taskModel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }
}
