package ru.tsu.hits.springdb2.dto.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tsu.hits.springdb2.dto.CreateUpdateProjectDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateTaskDto;
import ru.tsu.hits.springdb2.dto.ProjectDto;
import ru.tsu.hits.springdb2.dto.TaskDto;
import ru.tsu.hits.springdb2.entity.CommentEntity;
import ru.tsu.hits.springdb2.entity.ProjectEntity;
import ru.tsu.hits.springdb2.entity.TaskEntity;
import ru.tsu.hits.springdb2.entity.UserEntity;
import ru.tsu.hits.springdb2.repository.CommentRepository;
import ru.tsu.hits.springdb2.repository.TaskRepository;
import ru.tsu.hits.springdb2.repository.UserRepository;
import ru.tsu.hits.springdb2.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskDtoConverter {


    public static TaskEntity convertDtoToEntity(CreateUpdateTaskDto createUpdateTaskDto, UserEntity userCreator, UserEntity userEditor, ProjectEntity project){
        TaskEntity taskEntity = new TaskEntity();


        taskEntity.setUuid(UUID.randomUUID().toString());
        taskEntity.setCreationDate(createUpdateTaskDto.getCreationDate());
        taskEntity.setEditDate(createUpdateTaskDto.getEditDate());
        taskEntity.setDescription(createUpdateTaskDto.getDescription());
        taskEntity.setPriority(createUpdateTaskDto.getPriority());
        taskEntity.setTimeMark(createUpdateTaskDto.getTimeMark());
        taskEntity.setName(createUpdateTaskDto.getName());
        taskEntity.setUserCreator(userCreator);
        taskEntity.setUserEditor(userEditor);
        taskEntity.setProject(project);

        return taskEntity;
    }

    public static TaskDto convertEntityToDto(TaskEntity taskEntity, List<CommentEntity> commentEntities){
        TaskDto taskDto = new TaskDto();

        taskDto.setId(taskEntity.getUuid());
        taskDto.setCreationDate(taskEntity.getCreationDate());
        taskDto.setEditDate(taskEntity.getEditDate());
        taskDto.setName(taskEntity.getName());
        taskDto.setPriority(taskEntity.getPriority());
        taskDto.setDescription(taskEntity.getDescription());
        taskDto.setTimeMark(taskEntity.getTimeMark());
        taskDto.setUserEditor(taskEntity.getUserEditor().getFullName());
        taskDto.setUserCreator(taskEntity.getUserCreator().getFullName());
        taskDto.setProject(taskEntity.getProject().getName());
        taskDto.setComments(CommentDtoConverter.convertCommentsToDto(commentEntities));

        return taskDto;
    }


    public static List<TaskDto> convertTasksToDto(List<TaskEntity> taskEntities){
        List<TaskDto> result = new ArrayList<>();

        taskEntities.forEach(element->{
            TaskDto taskDto = new TaskDto();

            taskDto.setCreationDate(element.getCreationDate());
            taskDto.setEditDate(element.getEditDate());
            taskDto.setDescription(element.getDescription());
            taskDto.setId(element.getUuid());
            taskDto.setName(element.getName());
            taskDto.setPriority(element.getPriority());
            taskDto.setUserCreator(element.getUserCreator().getFullName());
            taskDto.setUserEditor(element.getUserEditor().getFullName());
            taskDto.setTimeMark(element.getTimeMark());
            taskDto.setProject(element.getProject().getName());
//            taskDto.setComments(CommentDtoConverter.convertCommentsToDto(element.getComments()));

            result.add(taskDto);
        });
        return result;
    }
}
