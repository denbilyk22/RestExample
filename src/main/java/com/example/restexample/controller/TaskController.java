package com.example.restexample.controller;

import com.example.restexample.entity.Task;
import com.example.restexample.model.TaskModel;
import com.example.restexample.service.task_service.TaskService;
import com.example.restexample.service.user_service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

//    @PostMapping
//    @ResponseBody
//    public ResponseEntity<?> create(@RequestBody Task task, @RequestParam Long userId){
//
//        taskService.create(task, userId);
//
//        Long id = task.getId();
//        return new ResponseEntity<>("Task #" + id + " has been created", HttpStatus.CREATED);
//    }

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

//    @GetMapping
//    @ResponseBody
//    public ResponseEntity<?> readAll(){
//        final List<Task> tasks = taskServiceInterface.readAll();
//
//        if(tasks != null && !tasks.isEmpty()){
//            final List<TaskModel> taskModels = taskServiceInterface.readAll().stream()
//                                                .map(TaskModel::toModel).collect(Collectors.toList());
//
//            return new ResponseEntity<>(taskModels, HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>("Tasks haven`t been founded", HttpStatus.NOT_FOUND);
//    }

    @GetMapping
    public String readAllTasks(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        if(userName.equalsIgnoreCase("admin")){
            model.addAttribute("tasks", taskService.readAll().stream()
                    .map(TaskModel::toModel).collect(Collectors.toList()));
            return "tasks_templates/tasks_list_admin";
        }

        model.addAttribute("tasks", taskService.readAll().stream()
                .map(TaskModel::toModel).collect(Collectors.toList()));
        return "tasks_templates/tasks_list";
    }

//    @GetMapping(value = "/{id}")
//    @ResponseBody
//    public ResponseEntity<?> read(@PathVariable Long id){
//        Task task = taskService.read(id);
//
//        if(task != null){
//            return new ResponseEntity<>(TaskModel.toModel(task), HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>("Task doesn`t exist", HttpStatus.NOT_FOUND);
//    }

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

//    @PutMapping(value = "/{id}")
//    @ResponseBody
//    public ResponseEntity<?> update(@RequestBody Task task, @PathVariable Long id){
//
//        if(taskService.update(task, id)){
//            return new ResponseEntity<>("Task #" + id + " has been updated" , HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>("Task #" + id + " doesn`t exist", HttpStatus.NOT_FOUND);
//    }

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

//    @DeleteMapping(value = "/{id}")
//    @ResponseBody
//    public ResponseEntity<?> delete(@PathVariable Long id){
//
//        if(taskService.delete(id)){
//            return new ResponseEntity<>("Task #" + id + " has been deleted", HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>("Task #" + id + " doesn`t exist", HttpStatus.NOT_FOUND);
//    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id){
        taskService.delete(id);
        return "redirect:/tasks";
    }
}
