package com.example.restexample.controller;

import com.example.restexample.entity.Task;
import com.example.restexample.model.TaskModel;
import com.example.restexample.service.task_service.TaskServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/tasks")
public class TaskController {

    @Autowired
    private TaskServiceInterface taskServiceInterface;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Task task, @RequestParam Long userId){

        taskServiceInterface.create(task, userId);

        Long id = task.getId();
        return new ResponseEntity<>("Task #" + id + " has been created", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> readAll(){
        final List<Task> tasks = taskServiceInterface.readAll();

        if(tasks != null && !tasks.isEmpty()){
            final List<TaskModel> taskModels = taskServiceInterface.readAll().stream()
                                                .map(TaskModel::toModel).collect(Collectors.toList());

            return new ResponseEntity<>(taskModels, HttpStatus.OK);
        }

        return new ResponseEntity<>("Tasks haven`t been founded", HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> read(@PathVariable Long id){
        Task task = taskServiceInterface.read(id);

        if(task != null){
            return new ResponseEntity<>(TaskModel.toModel(task), HttpStatus.OK);
        }

        return new ResponseEntity<>("Task doesn`t exist", HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@RequestBody Task task, @PathVariable Long id){

        if(taskServiceInterface.update(task, id)){
            return new ResponseEntity<>("Task #" + id + " has been updated" , HttpStatus.OK);
        }

        return new ResponseEntity<>("Task #" + id + " doesn`t exist", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

        if(taskServiceInterface.delete(id)){
            return new ResponseEntity<>("Task #" + id + " has been deleted", HttpStatus.OK);
        }

        return new ResponseEntity<>("Task #" + id + " doesn`t exist", HttpStatus.NOT_FOUND);
    }
}
