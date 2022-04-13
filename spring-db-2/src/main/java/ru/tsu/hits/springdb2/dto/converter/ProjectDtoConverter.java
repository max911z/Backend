package ru.tsu.hits.springdb2.dto.converter;

import ru.tsu.hits.springdb2.dto.*;
import ru.tsu.hits.springdb2.entity.CommentEntity;
import ru.tsu.hits.springdb2.entity.ProjectEntity;
import ru.tsu.hits.springdb2.entity.TaskEntity;
import ru.tsu.hits.springdb2.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ProjectDtoConverter {

    public static ProjectEntity convertDtoToEntity(CreateUpdateProjectDto createUpdateProjectDto){
        ProjectEntity projectEntity = new ProjectEntity();

        projectEntity.setUuid(UUID.randomUUID().toString());
        projectEntity.setCreationDate(createUpdateProjectDto.getCreationDate());
        projectEntity.setEditDate(createUpdateProjectDto.getEditDate());
        projectEntity.setDescription(createUpdateProjectDto.getDescription());
        projectEntity.setName(createUpdateProjectDto.getName());

        return projectEntity;
    }

    public static ProjectDto convertEntityToDto(ProjectEntity projectEntity, List<TaskEntity> taskEntities){
        ProjectDto projectDto = new ProjectDto();

        projectDto.setId(projectEntity.getUuid());
        projectDto.setName(projectEntity.getName());
        projectDto.setDescription(projectEntity.getDescription());
        projectDto.setCreationDate(projectEntity.getCreationDate());
        projectDto.setEditDate(projectEntity.getEditDate());
//        projectDto.setTasks(TaskDtoConverter.convertTasksToDto(taskEntities));

        return projectDto;
    }





}
