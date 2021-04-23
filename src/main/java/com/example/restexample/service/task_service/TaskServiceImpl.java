package com.example.restexample.service.task_service;

import com.example.restexample.entity.Task;
import com.example.restexample.entity.User;
import com.example.restexample.repository.TaskRepository;
import com.example.restexample.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    //Create new task for specific user in repository
    @Override
    public void create(Task task, Long userId) {
        User user = userRepository.findById(userId).get();
        task.setUser(user);

        taskRepository.save(task);
    }

    //Find tasks by id in repository
    @Override
    public Task read(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    //Find all existing tasks in repository
    @Override
    public List<Task> readAll() {
        return taskRepository.findAll();
    }

    //Find all existing tasks in repository with pagination
    @Override
    public Page<Task> readAllPaginated(int pageNumber, int pageSize) {
        Pageable pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return taskRepository.findAll(pageRequest);
    }

    //Update task information
    @Override
    public boolean update(Task task, Long id) {
        if(taskRepository.existsById(id)){
            task.setId(id);
            taskRepository.save(task);
            return true;
        }

        return false;
    }

    //Delete task from repository
    @Override
    public boolean delete(Long id) {
        if(taskRepository.existsById(id)){
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
