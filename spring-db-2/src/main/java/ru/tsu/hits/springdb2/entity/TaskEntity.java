package ru.tsu.hits.springdb2.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {

    @Id
    @Column(name = "id")
    private String uuid;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "creation_date")
    private Date creationDate;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "edit_date")
    private Date editDate;

    @Column
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private UserEntity userCreator;

    @ManyToOne
    @JoinColumn(name = "editor_id",referencedColumnName = "id")
    private UserEntity userEditor;

    @Column
    private String priority;

    @ManyToOne
    @JoinColumn(name = "project_id",referencedColumnName = "id")
    private ProjectEntity project;

    @Column(name = "time_mark")
    private String timeMark;

    @ManyToMany(mappedBy = "tasks")
    private List<CommentEntity> comments;
}
