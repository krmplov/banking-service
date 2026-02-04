package com.krmplov.mappers;

import com.krmplov.createRequest.CreateUserRequest;
import com.krmplov.dto.UserDto;
import com.krmplov.models.Account;
import com.krmplov.models.User;

public class UserMapper {
    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.set_login(user.get_login());
        userDto.set_age(user.get_age());
        userDto.set_gender(user.get_gender());
        userDto.set_hairColor(user.get_hairColor());
        userDto.set_accounts(user.get_accounts().stream()
                .map(Account::get_id)
                .toList());
        userDto.set_friendships(user.get_friendships());
        userDto.set_name(user.get_name());

        return userDto;
    }

    public static User toEntity(CreateUserRequest user) {
        User userEntity = new User();
        userEntity.set_login(user.get_login());
        userEntity.set_age(user.get_age());
        userEntity.set_gender(user.get_gender());
        userEntity.set_hairColor(user.get_hairColor());
        userEntity.set_name(user.get_name());

        return userEntity;
    }
}
