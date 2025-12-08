package com.shoppingbackend.flywheel_store.service.user;

import com.shoppingbackend.flywheel_store.dto.UserDto;
import com.shoppingbackend.flywheel_store.model.User;
import com.shoppingbackend.flywheel_store.request.CreateUserRequest;
import com.shoppingbackend.flywheel_store.request.UpdateUserRequest;

public interface IUserService {
    User getUserById(Long userId);
    User createNewUser(CreateUserRequest request);
    User updateUser(UpdateUserRequest request, Long userId);
    void deleteUser(Long userId);
    UserDto convertedUserToDto(User user);
    User getAuthenticatedUser();
}
