package ru.tsu.hits.springdb2.service;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsu.hits.springdb2.dto.CreateUpdateProjectDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateUserDto;
import ru.tsu.hits.springdb2.dto.ProjectDto;
import ru.tsu.hits.springdb2.dto.UserDto;
import ru.tsu.hits.springdb2.dto.converter.ProjectDtoConverter;
import ru.tsu.hits.springdb2.dto.converter.UserDtoConverter;
import ru.tsu.hits.springdb2.entity.CommentEntity;
import ru.tsu.hits.springdb2.entity.ProjectEntity;
import ru.tsu.hits.springdb2.entity.TaskEntity;
import ru.tsu.hits.springdb2.entity.UserEntity;
import ru.tsu.hits.springdb2.exception.ProjectNotFoundException;
import ru.tsu.hits.springdb2.exception.UserNotFoundException;
import ru.tsu.hits.springdb2.repository.CommentRepository;
import ru.tsu.hits.springdb2.repository.ProjectRepository;
import ru.tsu.hits.springdb2.repository.TaskRepository;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private  final ProjectRepository projectRepository;

    private final TaskRepository taskRepository;

    @Transactional
    public ProjectDto createProject(CreateUpdateProjectDto createUpdateProjectDto){

        ProjectEntity projectEntity = ProjectDtoConverter.convertDtoToEntity(createUpdateProjectDto);

        projectEntity = projectRepository.save(projectEntity);

        return  ProjectDtoConverter.convertEntityToDto(projectEntity,getTasksByProject(projectEntity));
    }

    @Transactional(readOnly = true)
    public  ProjectDto getProjectDtoById(String uuid){
        ProjectEntity projectEntity = getProjectEntityById(uuid);
        return ProjectDtoConverter.convertEntityToDto(projectEntity,getTasksByProject(projectEntity));
    }

    @Transactional(readOnly = true)
    public ProjectEntity getProjectEntityById(String uuid) {
        return projectRepository.findById(uuid)
                .orElseThrow(()->new ProjectNotFoundException("Проект с id " + uuid + " не найден!"));

    }

    @Transactional(readOnly = true)
    public List<TaskEntity> getTasksByProject(ProjectEntity projectEntity){
        return taskRepository.findByProject(projectEntity);
    }

    public List<ProjectDto> saveFromFile() {

        ArrayList<ProjectDto> projectsList = new ArrayList<>();

        var csvStream = ProjectService.class.getResourceAsStream("/projects.csv");
        var projects = new CsvToBeanBuilder<CreateUpdateProjectDto>(new InputStreamReader(Objects.requireNonNull(csvStream)))
                .withSeparator(',')
                .withType(CreateUpdateProjectDto.class)
                .withSkipLines(1)
                .build()
                .parse();
        projects.stream().collect(Collectors.toList());

        for (CreateUpdateProjectDto project : projects) {
            ProjectEntity projectEntity = ProjectDtoConverter.convertDtoToEntity(project);
            projectEntity = projectRepository.save(projectEntity);
            projectsList.add(ProjectDtoConverter.convertEntityToDto(projectEntity,getTasksByProject(projectEntity)));
        }
        return projectsList;
    }

}
