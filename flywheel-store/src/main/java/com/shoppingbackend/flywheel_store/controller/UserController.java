package com.shoppingbackend.flywheel_store.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingbackend.flywheel_store.dto.UserDto;
import com.shoppingbackend.flywheel_store.exceptions.AlreadyExistsException;
import com.shoppingbackend.flywheel_store.exceptions.ResourceNotFoundException;
import com.shoppingbackend.flywheel_store.model.User;
import com.shoppingbackend.flywheel_store.request.CreateUserRequest;
import com.shoppingbackend.flywheel_store.request.UpdateUserRequest;
import com.shoppingbackend.flywheel_store.response.ApiResponse;
import com.shoppingbackend.flywheel_store.service.user.IUserService;

import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final IUserService userService;

    @GetMapping("user/getUser/{userId}")
    public ResponseEntity <ApiResponse> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            UserDto convertedUser = userService.convertedUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("User Found", convertedUser));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));   
        }
    }

    @PostMapping("user/add")
    public ResponseEntity <ApiResponse> createUser(@RequestBody CreateUserRequest request){
        try {
            User user = userService.createNewUser(request);
            UserDto convertedUser = userService.convertedUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("User created", convertedUser));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/user/update/{userId}")
    public ResponseEntity <ApiResponse> updateUser(@RequestBody UpdateUserRequest request, @PathVariable Long userId){
        try {
            User user = userService.updateUser(request, userId);
            UserDto convertedUser = userService.convertedUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("User updated", convertedUser));
        } catch (ResourceNotFoundException e) {
           return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/user/delete/{userId}")
    public ResponseEntity <ApiResponse> deleteUser(@PathVariable Long userId){
        try {
              userService.deleteUser(userId);
              return ResponseEntity.ok(new ApiResponse("User deleted", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
