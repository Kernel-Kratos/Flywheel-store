package com.shoppingbackend.flywheel_store.service.user;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shoppingbackend.flywheel_store.dto.UserDto;
import com.shoppingbackend.flywheel_store.exceptions.AlreadyExistsException;
import com.shoppingbackend.flywheel_store.exceptions.ResourceNotFoundException;
import com.shoppingbackend.flywheel_store.model.User;
import com.shoppingbackend.flywheel_store.repository.UserRepository;
import com.shoppingbackend.flywheel_store.request.CreateUserRequest;
import com.shoppingbackend.flywheel_store.request.UpdateUserRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
    }

    @Override
    public User createNewUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req-> {
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    return userRepository.save(user);
                }).orElseThrow(() -> new AlreadyExistsException("Oops" + request.getEmail() + "This email already Exists"));
    }

    @Override
    public User updateUser(UpdateUserRequest request, Long userId) {
       return userRepository.findById(userId)
            .map(existingUser -> {
                existingUser.setFirstName(request.getFirstName());
                existingUser.setLastName(request.getLastName());
                return userRepository.save(existingUser);
            })
            .orElseThrow(()-> new ResourceNotFoundException("User Not found"));
    }

    @Override
    public void deleteUser(Long userId) {
       userRepository.findById(userId)
           .ifPresentOrElse(userRepository :: delete, ()-> {
                throw new ResourceNotFoundException("User not found");
           });
    }

    @Override
    public UserDto convertedUserToDto(User user){
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User getAuthenticatedUser() {
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       String email = authentication.getName();
       return userRepository.findByEmail(email);
    }

}
