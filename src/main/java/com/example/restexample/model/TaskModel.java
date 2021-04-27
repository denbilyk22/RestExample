package com.example.restexample.model;

import com.example.restexample.entity.Task;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Getter @Setter
public class TaskModel {

    private Long id;
    private String description;
    private Boolean completed;
    private String userNickname;

    //Converting Task entity to TaskModel
    public static TaskModel toModel(Task task){
        TaskModel taskModel = new TaskModel();
        taskModel.setId(task.getId());
        taskModel.setDescription(task.getDescription());
        taskModel.setCompleted(task.getCompleted());
        taskModel.setUserNickname(task.getUser().getNickname());
        return taskModel;
    }
}
