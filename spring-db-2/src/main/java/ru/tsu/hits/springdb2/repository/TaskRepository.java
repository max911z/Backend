package ru.tsu.hits.springdb2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.tsu.hits.springdb2.entity.CommentEntity;
import ru.tsu.hits.springdb2.entity.ProjectEntity;
import ru.tsu.hits.springdb2.entity.TaskEntity;
import ru.tsu.hits.springdb2.entity.UserEntity;

import java.util.Date;
import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity,String> {

    List<TaskEntity> findByUserCreator(UserEntity userEntity);

    List<TaskEntity> findByUserEditor(UserEntity userEntity);

    List<TaskEntity> findByProject(ProjectEntity projectEntity);

    List<TaskEntity> findByComments(CommentEntity commentEntity);

    List<TaskEntity> findAllByComments(CommentEntity commentEntity);

    List<TaskEntity> findAllByCreationDateAndEditDateAndDescriptionAndNameAndPriorityAndProjectAndUserCreatorAndUserEditorAndUuid(
            Date cD, Date eD, String description, String name, String priority, ProjectEntity project, UserEntity uC, UserEntity uE, String uuid
    );


}
