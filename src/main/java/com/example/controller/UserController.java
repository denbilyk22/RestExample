package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/users")
    public ResponseEntity<?> create(@RequestBody User user){
        userService.create(user);
        return new ResponseEntity<>("User " + user.getNickname() + " has been created",
                                    HttpStatus.CREATED);
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<?> read(@PathVariable(name = "id") int id){
        final User user = userService.read(id);

        if(user == null){
            return new ResponseEntity<>("User has not been founded",
                                        HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/users")
    public ResponseEntity<?> readAll(){
        final List<User> users = userService.readAll();

        if(users != null && !users.isEmpty()){
            return new ResponseEntity<List<User>>(users, HttpStatus.OK);
        }

        return new ResponseEntity<>("Users haven`t been founded", HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/users/{id}")
    public ResponseEntity<?> update(@RequestBody User user, @PathVariable(name = "id") int id){

        String nickname = null;

        if(userService.read(id) != null){
            nickname = userService.read(id).getNickname();
        }

        final boolean isUpdated= userService.update(user, id);

        if(isUpdated){
            return new ResponseEntity<>("User " + nickname + " has been updated",
                    HttpStatus.OK);
        }

        return new ResponseEntity<>("User does not exist", HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id){

        String nickname = null;

        if(userService.read(id) != null){
            nickname = userService.read(id).getNickname();
        }

        final boolean isDeleted= userService.delete(id);

        if(isDeleted){
            String message  = "User " + nickname + " has been deleted";
            return new ResponseEntity<>(message, HttpStatus.OK);
        }

        String message  = "User does not exist";
        return new ResponseEntity<>(message, HttpStatus.NOT_MODIFIED);
    }

}
