package com.example.demo.service;

import java.util.List;
import com.example.demo.model.User;

public interface UserService {

    void addUser(User user);

    void updateUser(User user);

    void delete(Long id);

    User getUserById(Long id);

    List<User> getAllUsers();

    void changeAdmin(Long id, boolean isAdmin);

    boolean isUserAdmin(Long id);
}
