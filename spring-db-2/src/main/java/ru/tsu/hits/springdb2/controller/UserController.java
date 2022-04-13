package ru.tsu.hits.springdb2.controller;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.springdb2.dto.CreateUpdateUserDto;
import ru.tsu.hits.springdb2.dto.SearchUserNoPasswordDto;
import ru.tsu.hits.springdb2.dto.UserDto;
import ru.tsu.hits.springdb2.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDto createUser(@RequestBody CreateUpdateUserDto createUpdateUserDto){
        return userService.createUser(createUpdateUserDto);
    }

    @PostMapping("/fromFile")
    public List<UserDto> saveFromFile(){
        return userService.saveFromFile();
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable UUID id){
        return userService.getUserDtoById(id.toString());
    }

    @PostMapping("/search/no-pass")
    public List<UserDto> getByAllNoPass(@RequestBody SearchUserNoPasswordDto dto){
        return userService.getUsersDtoByAllNoPass(dto);
    }

}
