package ru.tsu.hits.springdb2.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tsu.hits.springdb2.entity.CommentEntity;
import ru.tsu.hits.springdb2.entity.Role;
import ru.tsu.hits.springdb2.entity.TaskEntity;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String id;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date creationDate;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date editDate;

    private String fullName;

    private String email;

    private String password;

    private Role role;

//    private List<TaskDto> createdTasks;
//
//    private List<TaskDto> editedTasks;
//
//    private List<CommentDto> comments;
}
