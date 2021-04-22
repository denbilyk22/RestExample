package com.example.restexample.controller;

import com.example.restexample.entity.User;
import com.example.restexample.model.UserModel;
import com.example.restexample.service.user_service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return "users_templates/create_user_form";
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
//                        .map(UserModel::toModel).collect(Collectors.toList());
//
//                return new ResponseEntity<>(userModelsAdmin, HttpStatus.OK);
//            }
//
//            final List<UserModel> userModels = users.stream()
//                    .map(UserModelUser::toModel).collect(Collectors.toList());
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

        model.addAttribute("users", userList.stream()
                .map(UserModel::toModel).collect(Collectors.toList()));

        if(userName.equalsIgnoreCase("admin")){
            return "users_templates/users_list_admin";
        }

        return "users_templates/users_list";
    }

//    @GetMapping(value = "/{id}")
//    @ResponseBody
//    public ResponseEntity<?> read(@PathVariable(name = "id") Long id){
//        final User user = userService.read(id);
//
//        if(user == null){
//            return new ResponseEntity<>("User doesn`t exist",
//                                        HttpStatus.NOT_FOUND);
//        }
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String userName = auth.getName();
//
//        if(userName.equalsIgnoreCase("admin")){
//            UserModel userModel = UserModel.toModel(user);
//            return new ResponseEntity<>(userModel, HttpStatus.OK);
//        }
//
//        else {
//            UserModelUser userModelUser = UserModelUser.toModel(user);
//            return new ResponseEntity<>(userModelUser, HttpStatus.OK);
//        }
//    }

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

//    @PutMapping(value = "/{id}")
//    @ResponseBody
//    public ResponseEntity<?> update(@RequestBody User user, @PathVariable(name = "id") Long id){
//
//        String nickname = null;
//
//        if(userService.read(id) != null){
//            nickname = userService.read(id).getNickname();
//        }
//
//        final boolean isUpdated = userService.update(user, id);
//
//        if(isUpdated){
//            return new ResponseEntity<>("User " + nickname + " has been updated",
//                    HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>("User doesn`t exist", HttpStatus.NOT_MODIFIED);
//    }

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

//    @DeleteMapping(value = "{id}")
//    @ResponseBody
//    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id){
//
//        String nickname = null;
//
//        if(userService.read(id) != null){
//            nickname = userService.read(id).getNickname();
//        }
//
//        final boolean isDeleted= userService.delete(id);
//
//        if(isDeleted){
//            String message  = "User " + nickname + " has been deleted";
//            return new ResponseEntity<>(message, HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>("User doesn`t exist", HttpStatus.NOT_MODIFIED);
//    }

    @DeleteMapping(value = "{id}")
    public String deleteUser(@PathVariable Long id){
        userService.delete(id);
        return "redirect:/users";
    }
}
