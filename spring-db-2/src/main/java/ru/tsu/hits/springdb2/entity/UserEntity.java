package ru.tsu.hits.springdb2.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

   
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

    @Column(name = "full_name")
    private String fullName;

    @Column
    private String email;

    @Column(name = "password_hash")
    private String passHash;

    @Enumerated(EnumType.STRING)
    @Column
    private Role role;

    @OneToMany(mappedBy = "userCreator",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TaskEntity> createdTasks;

    @OneToMany(mappedBy = "userEditor",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TaskEntity> editedTasks;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CommentEntity> comments;

}
