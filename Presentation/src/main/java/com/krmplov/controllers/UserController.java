package com.krmplov.controllers;

import com.krmplov.createRequest.CreateUserRequest;
import com.krmplov.dto.UserDto;
import com.krmplov.models.Gender;
import com.krmplov.models.HairColor;
import com.krmplov.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "user management", description = "operations with bank user")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService _userService;

    @Operation(summary = "get all users", description = "returns all users details")
    @GetMapping
    public ResponseEntity<?> listUsers() {
        List<UserDto> userDtos = _userService.listUsers();
        return ResponseEntity.ok(userDtos);
    }

    @Operation(summary = "get user by login", description = "returns user details")
    @GetMapping("{login}")
    public ResponseEntity<?> getUserByLogin(@PathVariable("login") String login) {
        UserDto userDto = _userService.getUserByLogin(login);
        return ResponseEntity.ok(userDto);
    }

    @Operation(summary = "save user", description = "save user in Database")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateUserRequest user) {
        _userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "delete user by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("{login}")
    public ResponseEntity<?> deleteUser(@RequestParam String login) {
        _userService.deleteUser(login);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "get friends", description = "returns a list of the user's friends by username")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("{login}/friends")
    public ResponseEntity<?> getFriends(@PathVariable("login") String login) {
        UserDto userDto = _userService.getUserByLogin(login);
        List<String> friends = new ArrayList<>(userDto.get_friendships());
        return ResponseEntity.ok(friends);
    }

    @Operation(summary = "add friendship", description = "make users friends")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("friends")
    public ResponseEntity<?> addFriend(@RequestParam String userFirst,
                                          @RequestParam String userSecond) {
        _userService.addFriend(userFirst, userSecond);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "remove friendship", description = "deleting each other from friends")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("remove_friend")
    public ResponseEntity<?> removeFriend(@RequestParam String userFirst,
                                             @RequestParam String userSecond) {
        _userService.removeFriend(userFirst, userSecond);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "filtering by hair color and gender", description = "returns a list of suitable users")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("filter")
    public ResponseEntity<List<UserDto>> filterUsers(@RequestParam(required = false) HairColor hairColor,
                                                     @RequestParam(required = false) Gender gender) {
        List<UserDto> userDtos = _userService.filterUsers(hairColor, gender);
        return ResponseEntity.ok(userDtos);
    }
}
