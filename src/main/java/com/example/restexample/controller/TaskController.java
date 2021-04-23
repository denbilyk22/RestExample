package com.example.restexample.controller;

import com.example.restexample.entity.Task;
import com.example.restexample.model.TaskModel;
import com.example.restexample.service.task_service.TaskService;
import com.example.restexample.service.user_service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @GetMapping("/createTask")
    public String createUserForm(Model model){
        Task task = new Task();
        model.addAttribute("task", task);
        model.addAttribute("users", userService.readAll());
        return "tasks_templates/create_task_form";
    }

    @PostMapping
    public String createUser(@ModelAttribute("task") Task task){
        taskService.create(task, task.getUser().getId());
        return "redirect:/tasks";
    }

    @GetMapping
    public String readAllTasks(Model model){
       return readAllTasksPaginated(1, model);
    }

    @GetMapping(params = {"page"})
    public String readAllTasksPaginated(@RequestParam("page") int pageNumber,Model model){
        int pageSize = 5;
        Page<Task> page = taskService.readAllPaginated(pageNumber, pageSize);
        List<Task> tasksList = page.getContent();

        List<TaskModel> taskModelList = tasksList.stream()
                .map(TaskModel::toModel).collect(Collectors.toList());

        model.addAttribute("tasks", taskModelList);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        if(userName.equalsIgnoreCase("admin")){
            return "tasks_templates/tasks_list_admin";
        }

        return "tasks_templates/tasks_list";
    }

    @GetMapping("/{id}")
    public String readTask(@PathVariable Long id, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        TaskModel task = TaskModel.toModel(taskService.read(id));
        model.addAttribute("task", task);

        if(userName.equalsIgnoreCase("admin")){
            return "tasks_templates/task_page_admin";
        }
        return "tasks_templates/task_page";
    }

    @GetMapping("/{id}/updateTask")
    public String updateTaskForm(@PathVariable Long id, Model model){
        Task task = taskService.read(id);
        model.addAttribute("task", task);
        model.addAttribute("users", userService.readAll());
        return "tasks_templates/update_task_form";
    }

    @PutMapping("/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute("task") Task task){
        taskService.update(task, id);
        return "redirect:/tasks/{id}";
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id){
        taskService.delete(id);
        return "redirect:/tasks";
    }
}
