package ru.tsu.hits.springdb2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tsu.hits.springdb2.entity.CommentEntity;
import ru.tsu.hits.springdb2.entity.TaskEntity;
import ru.tsu.hits.springdb2.entity.UserEntity;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity,String> {

    List<CommentEntity> findByUser(UserEntity userEntity);

    List<CommentEntity> findByTasks(TaskEntity taskEntity);

    List<CommentEntity> findAllByText(String text);
}
