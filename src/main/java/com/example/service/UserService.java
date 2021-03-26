package com.example.service;

import com.example.entity.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserServiceInterface {

    // Repository of users
    @Autowired
    private UserRepository userRepository;

    @Override
    public void create(User user) {
       userRepository.save(user);
    }

    @Override
    public User read(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> readAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean update(User user, Long id) {
        if(userRepository.existsById(id)){
            user.setId(id);
            userRepository.save(user);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(Long id) {
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
