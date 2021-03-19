package com.example.service;

import com.example.model.User;

import java.util.List;

public interface UserService {

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
    User read(int id);

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
    boolean update(User user, int id);

    /*
    Deleting user by ID
    @param id - id of user needed to delete
    @return - true if user was deleted, else false
    */
    boolean delete(int id);

}
