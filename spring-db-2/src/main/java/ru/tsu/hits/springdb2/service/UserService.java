package ru.tsu.hits.springdb2.service;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import ru.tsu.hits.springdb2.dto.CreateUpdateUserDto;
import ru.tsu.hits.springdb2.dto.SearchUserNoPasswordDto;
import ru.tsu.hits.springdb2.dto.UserDto;
import ru.tsu.hits.springdb2.dto.converter.UserDtoConverter;
import ru.tsu.hits.springdb2.entity.CommentEntity;
import ru.tsu.hits.springdb2.entity.TaskEntity;
import ru.tsu.hits.springdb2.entity.UserEntity;
import ru.tsu.hits.springdb2.exception.UserNotFoundException;
import ru.tsu.hits.springdb2.repository.CommentRepository;
import ru.tsu.hits.springdb2.repository.TaskRepository;
import ru.tsu.hits.springdb2.repository.UserRepository;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final TaskRepository taskRepository;

    private final CommentRepository commentRepository;

    @Transactional
    public UserDto createUser(CreateUpdateUserDto createUpdateUserDto){
        UserEntity userEntity = UserDtoConverter.convertDtoToEntity(createUpdateUserDto);

        userEntity = userRepository.save(userEntity);

        return UserDtoConverter.convertEntityToDto(userEntity);
    }

    @Transactional(readOnly = true)
    public  UserDto getUserDtoById(String uuid){
        UserEntity userEntity = getUserEntityById(uuid);
        return UserDtoConverter.convertEntityToDto(userEntity);
    }

    @Transactional(readOnly = true)
    public UserEntity getUserEntityById(String uuid) {
        return userRepository.findById(uuid)
                .orElseThrow(()->new UserNotFoundException("Пользователь с id " + uuid + " не найден!"));

    }

    @Transactional(readOnly = true)
    public List<CommentEntity> getCommentsByUser(UserEntity userEntity){
        return commentRepository.findByUser(userEntity);
    }

    @Transactional(readOnly = true)
    public List<TaskEntity> getCreatedTasksByUser(UserEntity userEntity){
        return taskRepository.findByUserCreator(userEntity);
    }

    @Transactional(readOnly = true)
    public List<TaskEntity> getEditedTasksByUser(UserEntity userEntity){
        return taskRepository.findByUserEditor(userEntity);
    }

    @Transactional
    public List<UserDto> getUsersDtoByAllNoPass(SearchUserNoPasswordDto dto){
        List<UserDto> userDtos = new ArrayList<>();
        userDtos =  userRepository.findAllByCreationDateAndEditDateAndFullNameAndEmailAndUuidAndRole(
                dto.getCreationDate(), dto.getEditDate(),dto.getFullName(),dto.getEmail(),dto.getId(),dto.getRole())
                .stream()
                .map(UserDtoConverter::convertEntityToDto).collect(Collectors.toList());
        return userDtos;
    }

    public List<UserDto> saveFromFile() {

        ArrayList<UserDto> usersList = new ArrayList<>();

        var csvStream = UserService.class.getResourceAsStream("/users.csv");
        var users = new CsvToBeanBuilder<CreateUpdateUserDto>(new InputStreamReader(Objects.requireNonNull(csvStream)))
                .withSeparator(',')
                .withType(CreateUpdateUserDto.class)
                .withSkipLines(1)
                .build()
                .parse();
        users.stream().collect(Collectors.toList());

        for (CreateUpdateUserDto user : users) {
            UserEntity userEntity = UserDtoConverter.convertDtoToEntity(user);
            userEntity = userRepository.save(userEntity);
            usersList.add(UserDtoConverter.convertEntityToDto(userEntity));
        }
        return usersList;
    }

}
