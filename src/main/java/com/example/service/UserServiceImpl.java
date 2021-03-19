package com.example.service;

import com.example.model.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UserServiceImpl implements UserService{

    // Repository of users
    private static Map<Integer, User> USER_REPOSITORY = new HashMap<Integer,User>();

    // Generating user ID
    private static AtomicInteger USER_ID = new AtomicInteger();

    @Override
    public void create(User user) {
        final int userId = USER_ID.incrementAndGet();
        user.setId(userId);
        USER_REPOSITORY.put(userId, user);
    }

    @Override
    public User read(int id) {
        return USER_REPOSITORY.get(id);
    }

    @Override
    public List<User> readAll() {
        return new ArrayList<>(USER_REPOSITORY.values());
    }

    @Override
    public boolean update(User user, int id) {
        if(USER_REPOSITORY.containsKey(id)){
            user.setId(id);
            USER_REPOSITORY.put(id, user);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        return USER_REPOSITORY.remove(id) != null;
    }


}
