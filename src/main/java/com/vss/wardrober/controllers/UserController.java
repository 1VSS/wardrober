package com.vss.wardrober.controllers;

import com.vss.wardrober.DTOs.UserDTO;
import com.vss.wardrober.models.UserModel;
import com.vss.wardrober.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserModel> getUsers() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserModel getUser(@PathVariable Long id) {

        Optional<UserModel> user = userService.findById(id);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return user.get();
    }

    @PostMapping
    public ResponseEntity<UserModel> postUser(@Valid
                                              @RequestBody UserDTO userDTO) {

        var user = new UserModel();
        BeanUtils.copyProperties(userDTO, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {

        Optional<UserModel> user = userService.findById(id);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserModel> putUser(@Valid
                                             @PathVariable Long id,
                                             @RequestBody UserDTO userDTO) {

        Optional<UserModel> user = userService.findById(id);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDTO, userModel);
        userModel.setId(user.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(userService.save(userModel));
    }
}


