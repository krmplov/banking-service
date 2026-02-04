package com.krmplov.services;

import com.krmplov.createRequest.CreateUserRequest;
import com.krmplov.dto.AccountTopicDto;
import com.krmplov.dto.UserDto;
import com.krmplov.dto.UserTopicDto;
import com.krmplov.exceptions.BusinessException;
import com.krmplov.kafka.KafkaProducer;
import com.krmplov.mappers.UserMapper;
import com.krmplov.models.*;
import com.krmplov.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository _userRepository;
    private final KafkaProducer kafkaProducer;

    public List<UserDto> listUsers() {
        try {
            List<User> users = _userRepository.findAll();
            List<UserDto> userDtos = new ArrayList<>();
            for (User user : users) {
                userDtos.add(UserMapper.toDto(user));
            }
            return userDtos;
        } catch (Exception e) {
            throw new BusinessException(Message.DbError);
        }
    }

    public UserDto getUserByLogin(String login) {
        User user = _userRepository.findBy_login(login).orElseThrow(() -> new BusinessException(Message.AccountNotFound));
        return UserMapper.toDto(user);
    }

    public void saveUser(CreateUserRequest user) {
        if (user.get_age() <= 0) {
            throw new BusinessException(Message.ExeptionsAge);
        }
        try {
            if (_userRepository.existsById(user.get_login())) {
                throw new BusinessException(Message.UserAlreadyExists);
            }
            _userRepository.save(UserMapper.toEntity(user));
            UserTopicDto userTopicDto = new UserTopicDto();
            userTopicDto.setMessage(TopicMessage.USER_CREATED.getMessage());
            kafkaProducer.send(user.get_login(), userTopicDto, TopicMessage.USER_CREATED.getTopic());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BusinessException(Message.DbError);
        }
    }

    public void deleteUser(String login) {
        if (_userRepository.existsById(login)) {
            _userRepository.deleteById(login);
        }
        throw new BusinessException(Message.UserNotFound);
    }

    public void addFriend(String userFirst, String userSecond) {
        if (userFirst.equals(userSecond)) {
            throw new BusinessException(Message.TheSameUser);
        }

        _userRepository.findBy_login(userFirst)
                .map(user -> {
                    if (user.get_friendships().contains(userSecond)) {
                        throw new BusinessException(Message.AlreadyFriends);
                    }

                    user.get_friendships().add(userSecond);
                    _userRepository.save(user);
                    return true;
                })
                .orElseThrow(() -> new BusinessException(Message.UserNotFound));

        _userRepository.findBy_login(userSecond)
                .map(user -> {
                    if (user.get_friendships().contains(userFirst)) {
                        throw new BusinessException(Message.AlreadyFriends);
                    }

                    user.get_friendships().add(userFirst);
                    _userRepository.save(user);
                    return true;
                })
                .orElseThrow(() -> new BusinessException(Message.UserNotFound));

        UserTopicDto userTopicDto = new UserTopicDto();
        userTopicDto.setMessage(TopicMessage.USER_ADD_FRIEND.format(userSecond));
        kafkaProducer.send(userFirst, userTopicDto, TopicMessage.USER_ADD_FRIEND.getTopic());
    }

    public void removeFriend(String userLogin, String friendLogin) {
        _userRepository.findBy_login(userLogin)
                .map(user -> {
                    if (!user.get_friendships().contains(friendLogin)) {
                        throw new BusinessException(Message.UserNotFound);
                    }

                    user.get_friendships().remove(friendLogin);
                    _userRepository.save(user);
                    return true;
                })
                .orElseThrow(() -> new BusinessException(Message.UserNotFound));

        UserTopicDto userTopicDto = new UserTopicDto();
        userTopicDto.setMessage(TopicMessage.USER_REMOVE_FRIEND.format(friendLogin));
        kafkaProducer.send(userLogin, userTopicDto, TopicMessage.USER_REMOVE_FRIEND.getTopic());
    }

    public List<UserDto> filterUsers(HairColor hairColor, Gender gender) {
        try {
            List<User> users = _userRepository.findAll().stream()
                    .filter(user -> hairColor == null || user.get_hairColor() == hairColor)
                    .filter(user -> gender == null || user.get_gender() == gender)
                    .toList();
            List<UserDto> userDtos = new ArrayList<>();
            for (User user : users) {
                userDtos.add(UserMapper.toDto(user));
            }
            return userDtos;
        } catch (Exception e) {
            throw new BusinessException(Message.DbError);
        }
    }
}
