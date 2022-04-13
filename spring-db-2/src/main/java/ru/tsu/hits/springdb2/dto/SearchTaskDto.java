package ru.tsu.hits.springdb2.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SearchTaskDto {
    private String id;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date creationDate;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date editDate;

    private String name;

    private String description;

    private String userCreatorId;

    private String userEditorId;

    private String priority;

    private String projectId;

}
