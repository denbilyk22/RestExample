package com.example.restexample.service;

import com.example.restexample.entity.User;

import java.util.List;

public interface UserServiceInterface {

    /*
    Creating new user
    @param user - user to create
    */
    void create(User user);

    /*
    Reading user from repository by id
    @param id - user id
    @return - user instance with specified id
    */
    User read(Long id);

    /*
    Reading all users from repository
    @return - list of existing users
    */
    List<User> readAll();

    /*
    Updating user with specified ID according to user
    @param user - user that is base to update existing user
    @param id - id of user needed to update
    @return - true, if user was updated, else false
    */
    boolean update(User user, Long id);

    /*
    Deleting user by ID
    @param id - id of user needed to delete
    @return - true if user was deleted, else false
    */
    boolean delete(Long id);

}
