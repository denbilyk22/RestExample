package com.example.restexample.service.task_service;

import com.example.restexample.entity.Task;
import com.example.restexample.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService implements TaskServiceInterface{

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void create(Task task) {
        taskRepository.save(task);
    }

    @Override
    public Task read(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public List<Task> readAll() {
        return taskRepository.findAll();
    }

    @Override
    public boolean update(Task task, Long id) {
        if(taskRepository.existsById(id)){
            task.setId(id);
            taskRepository.save(task);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(Long id) {
        if(taskRepository.existsById(id)){
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
