package ru.tsu.hits.springdb2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tsu.hits.springdb2.entity.ProjectEntity;

public interface ProjectRepository extends JpaRepository<ProjectEntity,String> {
}
