package ru.tsu.hits.springdb2.dto.converter;

import ru.tsu.hits.springdb2.dto.CommentDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateCommentDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateTaskDto;
import ru.tsu.hits.springdb2.dto.TaskDto;
import ru.tsu.hits.springdb2.entity.CommentEntity;
import ru.tsu.hits.springdb2.entity.ProjectEntity;
import ru.tsu.hits.springdb2.entity.TaskEntity;
import ru.tsu.hits.springdb2.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommentDtoConverter {

    public static CommentEntity convertDtoToEntity(CreateUpdateCommentDto createUpdateCommentDto, UserEntity user){
        CommentEntity commentEntity = new CommentEntity();

        commentEntity.setUuid(UUID.randomUUID().toString());
        commentEntity.setCreationDate(createUpdateCommentDto.getCreationDate());
        commentEntity.setEditDate(createUpdateCommentDto.getEditDate());
        commentEntity.setText(createUpdateCommentDto.getText());
        commentEntity.setUser(user);



        return commentEntity;
    }

    public static CommentDto convertEntityToDto(CommentEntity commentEntity, List<TaskEntity> taskEntities){
        CommentDto commentDto = new CommentDto();

        commentDto.setId(commentEntity.getUuid());
        commentDto.setCreationDate(commentEntity.getCreationDate());
        commentDto.setEditDate(commentEntity.getEditDate());
        commentDto.setUser(commentEntity.getUser().getFullName());
        commentDto.setText(commentEntity.getText());
//        commentDto.setTasks(TaskDtoConverter.convertTasksToDto(taskEntities));

        return commentDto;
    }


    public static List<CommentDto> convertCommentsToDto(List<CommentEntity> commentsE){
        List<CommentDto> result = new ArrayList<>();

        commentsE.forEach(element->{
            CommentDto commentDto = new CommentDto();

            commentDto.setCreationDate(element.getCreationDate());
            commentDto.setEditDate(element.getEditDate());
            commentDto.setId(element.getUuid());
            commentDto.setText(element.getText());
            commentDto.setUser(element.getUser().getFullName());
//            commentDto.setTasks(TaskDtoConverter.convertTasksToDto(element.getTasks()));

            result.add(commentDto);
        });
        return result;
    }



}
