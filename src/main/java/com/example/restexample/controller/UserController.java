package com.example.restexample.controller;

import com.example.restexample.entity.User;
import com.example.restexample.model.UserModel;
import com.example.restexample.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    private UserServiceInterface userServiceInterface;

    @PostMapping(value = "/users")
    public ResponseEntity<?> create(@RequestBody User user){
        userServiceInterface.create(user);
        return new ResponseEntity<>("User " + user.getNickname() + " has been created",
                                    HttpStatus.CREATED);
    }

    @GetMapping(value = "/users")
    public ResponseEntity<?> readAll(){
        final List<User> users = userServiceInterface.readAll();

        if(users != null && !users.isEmpty()){

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userName = auth.getName();

            if(userName.equalsIgnoreCase("admin")){
                return new ResponseEntity<>(users, HttpStatus.OK);
            }

            final List<UserModel> userModels = users.stream()
                    .map(UserModel::toModel).collect(Collectors.toList());

            return new ResponseEntity<>(userModels, HttpStatus.OK);

        }

        return new ResponseEntity<>("Users haven`t been founded", HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<?> read(@PathVariable(name = "id") Long id){
        final User user = userServiceInterface.read(id);

        if(user == null){
            return new ResponseEntity<>("User has not been founded",
                                        HttpStatus.NOT_FOUND);
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        if(userName.equalsIgnoreCase("admin")){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        else {
            UserModel userModel = UserModel.toModel(user);
            return new ResponseEntity<>(userModel, HttpStatus.OK);
        }
    }

    @PutMapping(value = "/users/{id}")
    public ResponseEntity<?> update(@RequestBody User user, @PathVariable(name = "id") Long id){

        String nickname = null;

        if(userServiceInterface.read(id) != null){
            nickname = userServiceInterface.read(id).getNickname();
        }

        final boolean isUpdated = userServiceInterface.update(user, id);

        if(isUpdated){
            return new ResponseEntity<>("User " + nickname + " has been updated",
                    HttpStatus.OK);
        }

        return new ResponseEntity<>("User doesn`t exist", HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id){

        String nickname = null;

        if(userServiceInterface.read(id) != null){
            nickname = userServiceInterface.read(id).getNickname();
        }

        final boolean isDeleted= userServiceInterface.delete(id);

        if(isDeleted){
            String message  = "User " + nickname + " has been deleted";
            return new ResponseEntity<>(message, HttpStatus.OK);
        }

        return new ResponseEntity<>("User doesn`t exist", HttpStatus.NOT_MODIFIED);
    }

}
