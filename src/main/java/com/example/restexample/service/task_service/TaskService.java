package com.example.restexample.service.task_service;

import com.example.restexample.entity.Task;
import com.example.restexample.entity.User;
import com.example.restexample.repository.TaskRepository;
import com.example.restexample.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService implements TaskServiceInterface{

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void create(Task task, Long userId) {
        User user = userRepository.findById(userId).get();
        task.setUser(user);

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
