package ru.tsu.hits.springdb2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.springdb2.dto.*;
import ru.tsu.hits.springdb2.service.CommentService;
import ru.tsu.hits.springdb2.service.ProjectService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public CommentDto createComment(@RequestBody CreateUpdateCommentDto createUpdateCommentDto){
        return commentService.createComment(createUpdateCommentDto);
    }

    @GetMapping(value = "/{id}")
    public CommentDto getById(@PathVariable UUID id){
        return commentService.getCommentDtoById(id.toString());
    }

    @PostMapping("/search/by-text")
    public List<CommentDto> getByText(@RequestBody SearchByCommentTextDto dto){
        return commentService.getCommentsDtoByText(dto.getText());
    }

    @PostMapping("/fromFile")
    public List<CommentDto> saveFromFile(){
        return commentService.saveFromFile();
    }

}
