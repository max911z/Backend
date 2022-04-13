package ru.tsu.hits.springdb2.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.tsu.hits.springdb2.entity.TaskEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
public class ProjectDto {
    private String id;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date creationDate;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date editDate;

    private String name;

    private String description;

//    private List<TaskDto> tasks;
}
