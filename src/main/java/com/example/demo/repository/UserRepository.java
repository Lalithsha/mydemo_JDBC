package com.example.demo.repository;

import java.util.List;
import com.example.demo.model.*;

public interface UserRepository {

    int createUser(User user);

    int updateUser(User user);

    int deleteUser(Long id);

    User getUserById(Long id);

    List<User> getAllUsers();

    int changeAdmin(Long id, boolean isAdmin);
}
