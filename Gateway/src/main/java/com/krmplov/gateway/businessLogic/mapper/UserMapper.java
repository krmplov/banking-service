package com.krmplov.gateway.businessLogic.mapper;


import com.krmplov.gateway.dataAccess.dto.createRequest.CreateUserRequest;
import com.krmplov.gateway.dataAccess.dto.response.UserResponse;
import com.krmplov.gateway.dataAccess.model.User;
import com.krmplov.gateway.dataAccess.model.UserDetailsImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserMapper {
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public static UserDetailsImpl fromUserToUserDetails(User user) {
        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setUsername(user.getUsername());
        userDetails.setPassword(user.getPassword());
        userDetails.setRole(user.getRole());

        return userDetails;
    }

    public static User fromCreateUserRequestToUser(CreateUserRequest createUserRequest) {
        User user = new User();
        user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        user.setUsername(createUserRequest.getLogin());
        user.setRole(createUserRequest.getUserRole());

        return user;
    }

    public static UserResponse fromCreateUserRequestToUserResponse(CreateUserRequest createUserRequest) {
        UserResponse userResponse = new UserResponse(createUserRequest.getLogin(), createUserRequest.getName(), createUserRequest.getAge(), createUserRequest.getGender(), createUserRequest.getHairColor());

        return userResponse;
    }
}
