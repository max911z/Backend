package ru.tsu.hits.springdb2.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.tsu.hits.springdb2.entity.TaskEntity;
import ru.tsu.hits.springdb2.entity.UserEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
public class CommentDto {
    private String id;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date creationDate;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date editDate;

    private String user;

//    private List<TaskDto> tasks;

    private String text;
}
