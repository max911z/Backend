package ru.tsu.hits.springdb2.service;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsu.hits.springdb2.dto.*;
import ru.tsu.hits.springdb2.dto.converter.ProjectDtoConverter;
import ru.tsu.hits.springdb2.dto.converter.TaskDtoConverter;
import ru.tsu.hits.springdb2.dto.converter.UserDtoConverter;
import ru.tsu.hits.springdb2.entity.CommentEntity;
import ru.tsu.hits.springdb2.entity.ProjectEntity;
import ru.tsu.hits.springdb2.entity.TaskEntity;
import ru.tsu.hits.springdb2.entity.UserEntity;
import ru.tsu.hits.springdb2.exception.ProjectNotFoundException;
import ru.tsu.hits.springdb2.exception.TaskNotFoundException;
import ru.tsu.hits.springdb2.repository.CommentRepository;
import ru.tsu.hits.springdb2.repository.ProjectRepository;
import ru.tsu.hits.springdb2.repository.TaskRepository;
import ru.tsu.hits.springdb2.repository.UserRepository;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private  final ProjectRepository projectRepository;

    private final UserService userService;

    private final ProjectService projectService;

    @Transactional
    public TaskDto createTask(CreateUpdateTaskDto createUpdateTaskDto){

        var userCreator = userService.getUserEntityById(createUpdateTaskDto.getCreatorId());

        var userEditor = userService.getUserEntityById(createUpdateTaskDto.getEditorId());

        var project = projectService.getProjectEntityById(createUpdateTaskDto.getProjectId());

        TaskEntity taskEntity = TaskDtoConverter.convertDtoToEntity(createUpdateTaskDto,userCreator,userEditor,project);

        taskEntity = taskRepository.save(taskEntity);

        return TaskDtoConverter.convertEntityToDto(taskEntity,getCommentsByTask(taskEntity));

    }

    @Transactional(readOnly = true)
    public TaskDto getTaskDtoById(String uuid){
        TaskEntity taskEntity =getTaskEntityById(uuid);
        return TaskDtoConverter.convertEntityToDto(taskEntity,getCommentsByTask(taskEntity));
    }

    @Transactional(readOnly = true)
    public TaskEntity getTaskEntityById(String uuid) {
        return  taskRepository.findById(uuid)
                .orElseThrow(()->new TaskNotFoundException("Задача с id " + uuid + " не найдена!" ));
    }

    @Transactional(readOnly = true)
    public List<CommentEntity> getCommentsByTask(TaskEntity taskEntity){
        return commentRepository.findByTasks(taskEntity);
    }

    @Transactional(readOnly = true)
    public List<TaskDto> getTasksDtoByComments(List<CommentEntity> commentEntities){
        List<TaskEntity> taskEntities = new ArrayList<>();
        List<TaskDto> taskDtos = new ArrayList<>();
        commentEntities.forEach(comment -> taskEntities.addAll(taskRepository.findAllByComments(comment)));
        taskDtos = taskEntities.stream().map(taskEntity -> TaskDtoConverter.convertEntityToDto(taskEntity,getCommentsByTask(taskEntity))).collect(Collectors.toList());
//        taskDtos = TaskDtoConverter.convertTasksToDto(taskEntities);
        return taskDtos;
    }

    @Transactional(readOnly = true)
    public  List<TaskDto> getTasksByCommentsText(String text){
        List<CommentEntity> commentEntities = commentRepository.findAllByText(text);
        List<TaskDto> taskDtos = getTasksDtoByComments(commentEntities);
        return taskDtos;
    }

    @Transactional
    public List<TaskDto> getTasksDtoNoCommNoMark(SearchTaskDto dto){
        List<TaskDto> taskDtos = new ArrayList<>();
        UserEntity userCreator = userRepository.findById(dto.getUserCreatorId()).orElse(null);
        UserEntity userEditor = userRepository.findById(dto.getUserEditorId()).orElse(null);
        ProjectEntity project = projectRepository.findById(dto.getProjectId()).orElse(null);
        taskDtos =  taskRepository.findAllByCreationDateAndEditDateAndDescriptionAndNameAndPriorityAndProjectAndUserCreatorAndUserEditorAndUuid(
                        dto.getCreationDate(), dto.getEditDate(),dto.getDescription(),dto.getName(),dto.getPriority(),project,userCreator,userEditor, dto.getId() )
                .stream()
                .map(taskEntity -> TaskDtoConverter.convertEntityToDto(taskEntity,getCommentsByTask(taskEntity)))
                .collect(Collectors.toList());
        return taskDtos;
    }

    public List<TaskDto> saveFromFile() {

        ArrayList<TaskDto> tasksList = new ArrayList<>();

        var csvStream = TaskService.class.getResourceAsStream("/tasks.csv");
        var tasks = new CsvToBeanBuilder<CreateUpdateTaskDto>(new InputStreamReader(Objects.requireNonNull(csvStream)))
                .withSeparator(',')
                .withType(CreateUpdateTaskDto.class)
                .withSkipLines(1)
                .build()
                .parse();
        tasks.stream().collect(Collectors.toList());

        for (CreateUpdateTaskDto task : tasks) {
            var userCreator = userService.getUserEntityById(task.getCreatorId());
            var userEditor = userService.getUserEntityById(task.getEditorId());
            var project = projectService.getProjectEntityById(task.getProjectId());
            TaskEntity taskEntity = TaskDtoConverter.convertDtoToEntity(task,userCreator,userEditor,project);
            taskEntity = taskRepository.save(taskEntity);
            tasksList.add(TaskDtoConverter.convertEntityToDto(taskEntity,getCommentsByTask(taskEntity)));
        }
        return tasksList;
    }
}
