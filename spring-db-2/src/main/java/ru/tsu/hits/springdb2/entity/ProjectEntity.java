package ru.tsu.hits.springdb2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectEntity {

    @Id
    @Column(name = "id")
    private String uuid;

    @Temporal(TemporalType.DATE)
    @Column(name = "creation_date")
    private Date creationDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "edit_date")
    private Date editDate;

    @Column
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Column
    @Size(min = 10, message = "{validation.description.size.too_short}")
    @NotBlank(message = "Description is mandatory")
    private String description;

    @OneToMany(mappedBy = "project",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TaskEntity> tasks;

}
