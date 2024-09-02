package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.*;
import com.example.demo.service.UserService;
import com.example.demo.ApiResponse;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    // @Autowired
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        userService.addUser(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User user) {
        User existingUser = userService.getUserById(id);
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }

        if (!existingUser.getUsername().equals(user.getUsername())
                && userService.getAllUsers().stream().anyMatch(u -> u.getUsername().equals(user.getUsername()))) {
            return ResponseEntity.badRequest().body("Username already taken");
        }

        user.setId(id);
        userService.updateUser(user);
        // return ResponseEntity.ok(user);
        return ResponseEntity.ok().body("Updated user successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        User existingUser = userService.getUserById(id);
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }
        userService.delete(id);
        return ResponseEntity.ok(new ApiResponse(true, "User deleted successfully"));
    }

    @PatchMapping("/{id}/admin")
    public ResponseEntity<ApiResponse> toggleAdmin(@PathVariable Long id, @RequestParam(name="isAdmin") boolean isAdmin) {
        User existingUser = userService.getUserById(id);
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }
        userService.changeAdmin(id, isAdmin);
        return ResponseEntity.ok(new ApiResponse(true, "User admin status updated successfully"));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

}
