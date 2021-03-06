package com.example.restexample.service.task_service;

import com.example.restexample.entity.Task;
import com.example.restexample.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TaskService {

    /*
    Creating new task
    @param task - task to create
    */
    void create(Task task, Long userId);

    /*
    Reading task from repository by id
    @param id - task id
    @return - task instance with specific id
    */
    Task read(Long id);

    /*
    Reading all tasks from repository
    @return - list of existing tasks
    */
    List<Task> readAll();

    /*
    Reading all tasks from repository and divide content into pages
    @param pageNumber - number of page to display
    @param pageSize - number of tasks to display on the page
    @return - page of existing tasks
    */
    Page<Task> readAllPaginated(int pageNumber, int pageSize);

    /*
    Updating task with specific ID
    @param user - task that is base to update existing task
    @param id - id of task to update
    @return - true, if task was updated, else false
    */
    boolean update(Task task, Long id);

    /*
    Deleting task by ID
    @param id - id of task to delete
    @return - true if task was deleted, else false
    */
    boolean delete(Long id);
}
