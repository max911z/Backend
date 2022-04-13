package ru.tsu.hits.springdb2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.springdb2.dto.CreateUpdateProjectDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateUserDto;
import ru.tsu.hits.springdb2.dto.ProjectDto;
import ru.tsu.hits.springdb2.dto.UserDto;
import ru.tsu.hits.springdb2.service.CommentService;
import ru.tsu.hits.springdb2.service.ProjectService;
import ru.tsu.hits.springdb2.service.TaskService;
import ru.tsu.hits.springdb2.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/resource")
@RequiredArgsConstructor
public class resourceController {

    private final UserService userService;
    private final ProjectService projectService;
    private final TaskService taskService;
    private final CommentService commentService;

    @PostMapping()
    public void saveFromFile(){
         userService.saveFromFile();
         projectService.saveFromFile();
         taskService.saveFromFile();
         commentService.saveFromFile();
    }

}
