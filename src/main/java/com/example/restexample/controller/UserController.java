package com.example.restexample.controller;

import com.example.restexample.entity.User;
import com.example.restexample.model.UserModel;
import com.example.restexample.model.UserModelAdmin;
import com.example.restexample.service.user_service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

//    @PostMapping
//    @ResponseBody
//    public ResponseEntity<?> create(@RequestBody User user){
//        userService.create(user);
//        String nickname = user.getNickname();
//        return new ResponseEntity<>("User " + nickname + " has been created",
//                                    HttpStatus.CREATED);
//    }

    @GetMapping("/createUser")
    public String createUserForm(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "create_user_form";
    }

    @PostMapping
    public String createUser(@ModelAttribute("user") User user){
        userService.create(user);
        return "redirect:/users";
    }

//    @GetMapping
//    @ResponseBody
//    public ResponseEntity<?> readAll(){
//        final List<User> users = userServiceInterface.readAll();
//
//        if(users != null && !users.isEmpty()){
//
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            String userName = auth.getName();
//
//            if(userName.equalsIgnoreCase("admin")){
//                final List<UserModelAdmin> userModelsAdmin = users.stream()
//                        .map(UserModelAdmin::toModel).collect(Collectors.toList());
//
//                return new ResponseEntity<>(userModelsAdmin, HttpStatus.OK);
//            }
//
//            final List<UserModel> userModels = users.stream()
//                    .map(UserModel::toModel).collect(Collectors.toList());
//
//            return new ResponseEntity<>(userModels, HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>("Users haven`t been founded", HttpStatus.NOT_FOUND);
//    }

    @GetMapping
    public String readAllUsers(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        List<User> userList = userService.readAll();

        if(userName.equalsIgnoreCase("admin")){
            model.addAttribute("users", userList.stream()
                    .map(UserModelAdmin::toModel).collect(Collectors.toList()));
            return "users_list_admin";
        }

        model.addAttribute("users", userList.stream()
                .map(UserModel::toModel).collect(Collectors.toList()));
        return "users_list";
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<?> read(@PathVariable(name = "id") Long id){
        final User user = userService.read(id);

        if(user == null){
            return new ResponseEntity<>("User doesn`t exist",
                                        HttpStatus.NOT_FOUND);
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        if(userName.equalsIgnoreCase("admin")){
            UserModelAdmin userModelAdmin = UserModelAdmin.toModel(user);
            return new ResponseEntity<>(userModelAdmin, HttpStatus.OK);
        }

        else {
            UserModel userModel = UserModel.toModel(user);
            return new ResponseEntity<>(userModel, HttpStatus.OK);
        }
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<?> update(@RequestBody User user, @PathVariable(name = "id") Long id){

        String nickname = null;

        if(userService.read(id) != null){
            nickname = userService.read(id).getNickname();
        }

        final boolean isUpdated = userService.update(user, id);

        if(isUpdated){
            return new ResponseEntity<>("User " + nickname + " has been updated",
                    HttpStatus.OK);
        }

        return new ResponseEntity<>("User doesn`t exist", HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "{id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id){

        String nickname = null;

        if(userService.read(id) != null){
            nickname = userService.read(id).getNickname();
        }

        final boolean isDeleted= userService.delete(id);

        if(isDeleted){
            String message  = "User " + nickname + " has been deleted";
            return new ResponseEntity<>(message, HttpStatus.OK);
        }

        return new ResponseEntity<>("User doesn`t exist", HttpStatus.NOT_MODIFIED);
    }

}
