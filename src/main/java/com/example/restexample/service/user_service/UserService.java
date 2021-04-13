package com.example.restexample.service.user_service;

import com.example.restexample.entity.Task;
import com.example.restexample.entity.User;
import com.example.restexample.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private UserRepository userRepository;

    //Create new user in repository
    @Override
    public void create(User user) {
       userRepository.save(user);
    }

    //Find user in repository by id
    @Override
    public User read(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    //Find all existing users in repository
    @Override
    public List<User> readAll() {
        return userRepository.findAll();
    }

    //Updating user information
    @Override
    public boolean update(User user, Long id) {
        if(userRepository.existsById(id)){
            user.setId(id);
            userRepository.save(user);
            return true;
        }

        return false;
    }

    //Delete user by id if exists
    @Override
    public boolean delete(Long id) {
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
