package ru.tsu.hits.springdb2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.springdb2.dto.CreateUpdateProjectDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateUserDto;
import ru.tsu.hits.springdb2.dto.ProjectDto;
import ru.tsu.hits.springdb2.dto.UserDto;
import ru.tsu.hits.springdb2.service.ProjectService;
import ru.tsu.hits.springdb2.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ProjectDto createProject(@RequestBody CreateUpdateProjectDto createUpdateProjectDto){
        return projectService.createProject(createUpdateProjectDto);
    }

    @GetMapping("/{id}")
    public ProjectDto getById(@PathVariable UUID id){
        return projectService.getProjectDtoById(id.toString());
    }

    @PostMapping("/fromFile")
    public List<ProjectDto> saveFromFile(){
        return projectService.saveFromFile();
    }

}
