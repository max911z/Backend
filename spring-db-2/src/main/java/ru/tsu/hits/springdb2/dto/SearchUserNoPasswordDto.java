package ru.tsu.hits.springdb2.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.tsu.hits.springdb2.entity.Role;

import java.util.Date;

@Data
public class SearchUserNoPasswordDto {

    private String id;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date creationDate;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date editDate;

    private String fullName;

    private String email;

    private Role role;
}
