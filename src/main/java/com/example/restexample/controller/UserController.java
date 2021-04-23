package com.example.restexample.controller;

import com.example.restexample.entity.User;
import com.example.restexample.model.UserModel;
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
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/createUser")
    public String createUserForm(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "users_templates/create_user_form";
    }

    @PostMapping
    public String createUser(@ModelAttribute("user") User user){
        userService.create(user);
        return "redirect:/users";
    }

    @GetMapping
    public String readAllUsers(Model model){
        return readAllUsersPaginated(1, model);
    }

    @GetMapping(params = {"page"})
    public String readAllUsersPaginated(@RequestParam("page") int pageNumber,Model model){
        int pageSize = 5;
        Page<User> page = userService.readAllPaginated(pageNumber, pageSize);
        List<User> userList = page.getContent();

        List<UserModel> userModelList = userList.stream()
                .map(UserModel::toModel).collect(Collectors.toList());

        model.addAttribute("users", userModelList);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        if(userName.equalsIgnoreCase("admin")){
            return "users_templates/users_list_admin";
        }

        return "users_templates/users_list";
    }

    @GetMapping("/{id}")
    public String readUser(@PathVariable Long id, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        UserModel userModel = UserModel.toModel(userService.read(id));
        model.addAttribute("user", userModel);

        if(userName.equalsIgnoreCase("admin")){
            return "users_templates/user_page_admin";
        }
        return "users_templates/user_page";
    }

    @GetMapping("/{id}/updateUser")
    public String updateUserForm(@PathVariable Long id, Model model){
        User user = userService.read(id);
        model.addAttribute("user", user);
        return "users_templates/update_user_form";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute("user") User user){
        userService.update(user, id);
        return "redirect:/users/{id}";
    }

    @DeleteMapping(value = "{id}")
    public String deleteUser(@PathVariable Long id){
        userService.delete(id);
        return "redirect:/users";
    }
}
