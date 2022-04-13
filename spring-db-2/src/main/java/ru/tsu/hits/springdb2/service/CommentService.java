package ru.tsu.hits.springdb2.service;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsu.hits.springdb2.dto.CommentDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateCommentDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateTaskDto;
import ru.tsu.hits.springdb2.dto.TaskDto;
import ru.tsu.hits.springdb2.dto.converter.CommentDtoConverter;
import ru.tsu.hits.springdb2.dto.converter.TaskDtoConverter;
import ru.tsu.hits.springdb2.entity.CommentEntity;
import ru.tsu.hits.springdb2.entity.TaskEntity;
import ru.tsu.hits.springdb2.exception.CommentNotFoundException;
import ru.tsu.hits.springdb2.exception.TaskNotFoundException;
import ru.tsu.hits.springdb2.repository.CommentRepository;
import ru.tsu.hits.springdb2.repository.TaskRepository;


import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final TaskRepository taskRepository;

    private final UserService userService;

    public CommentDto createComment(CreateUpdateCommentDto createUpdateCommentDto){

        var user = userService.getUserEntityById(createUpdateCommentDto.getUserId());

        CommentEntity commentEntity = CommentDtoConverter.convertDtoToEntity(createUpdateCommentDto,user);

        commentEntity.setTasks(Arrays.stream(createUpdateCommentDto.getTasksId()).map(taskId -> taskRepository.findById(taskId).orElse(null)).collect(Collectors.toList()));

        commentEntity = commentRepository.save(commentEntity);

        return CommentDtoConverter.convertEntityToDto(commentEntity,getTasksByComments(commentEntity));

    }

    @Transactional(readOnly = true)
    public CommentDto getCommentDtoById(String uuid){
        CommentEntity commentEntity = getCommentEntityById(uuid);
        return CommentDtoConverter.convertEntityToDto(commentEntity,getTasksByComments(commentEntity));
    }

    @Transactional(readOnly = true)
    public CommentEntity getCommentEntityById(String uuid) {
        return commentRepository.findById(uuid)
                .orElseThrow(()->new CommentNotFoundException("Комментарий с id " + uuid + " не найден!" ));
    }

    @Transactional(readOnly = true)
    public List<TaskEntity> getTasksByComments(CommentEntity commentEntity){
        return taskRepository.findByComments(commentEntity);
    }

    @Transactional(readOnly = true)
    public List<CommentEntity> getCommentsByText(String text){
        return commentRepository.findAllByText(text);
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsDtoByText(String text){
        return CommentDtoConverter.convertCommentsToDto(commentRepository.findAllByText(text));
    }

    public List<CommentDto> saveFromFile() {

        ArrayList<CommentDto> commentsList = new ArrayList<>();

        var csvStream = CommentService.class.getResourceAsStream("/comments.csv");
        var comments = new CsvToBeanBuilder<CreateUpdateCommentDto>(new InputStreamReader(Objects.requireNonNull(csvStream)))
                .withSeparator(',')
                .withType(CreateUpdateCommentDto.class)
                .withSkipLines(1)
                .build()
                .parse();
        comments.stream().collect(Collectors.toList());


        for (CreateUpdateCommentDto comment : comments) {
            var user = userService.getUserEntityById(comment.getUserId());
            CommentEntity commentEntity = new CommentEntity();
//            System.out.println((comment.getTasksId().toString().split("/")));
            comment.setTasksId(comment.getTasksId().toString().split(";"));
            commentEntity = CommentDtoConverter.convertDtoToEntity(comment, user);
            commentEntity = commentRepository.save(commentEntity);
            commentsList.add(CommentDtoConverter.convertEntityToDto(commentEntity,getTasksByComments(commentEntity)));
        }
        return commentsList;
    }

}
