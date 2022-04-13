package ru.tsu.hits.springdb2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.springdb2.dto.*;
import ru.tsu.hits.springdb2.service.TaskService;
import ru.tsu.hits.springdb2.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public TaskDto createTask(@RequestBody CreateUpdateTaskDto createUpdateTaskDto){
        return taskService.createTask(createUpdateTaskDto);
    }

    @GetMapping("/{id}")
    public TaskDto getById(@PathVariable UUID id){
        return taskService.getTaskDtoById(id.toString());
    }

    @PostMapping("/search/by-comment-text")
    public List<TaskDto> findByCommentText(@RequestBody SearchByCommentTextDto dto){
        return taskService.getTasksByCommentsText(dto.getText());
    }

    @PostMapping("/search/no-comm")
    public List<TaskDto> findByNoCommNoMark(@RequestBody SearchTaskDto dto){
        return taskService.getTasksDtoNoCommNoMark(dto);
    }

    @PostMapping("/fromFile")
    public List<TaskDto> saveFromFile(){
        return taskService.saveFromFile();
    }

}
