package com.example.restexample.service.user_service;

import com.example.restexample.entity.User;
import com.example.restexample.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

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

    //Find all existing users in repository with pagination
    @Override
    public Page<User> readAllPaginated(int pageNumber, int pageSize) {
        Pageable pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return userRepository.findAll(pageRequest);
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
