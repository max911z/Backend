package ru.tsu.hits.springdb2.dto.converter;

import ru.tsu.hits.springdb2.dto.CommentDto;
import ru.tsu.hits.springdb2.dto.CreateUpdateUserDto;
import ru.tsu.hits.springdb2.dto.TaskDto;
import ru.tsu.hits.springdb2.dto.UserDto;
import ru.tsu.hits.springdb2.entity.CommentEntity;
import ru.tsu.hits.springdb2.entity.TaskEntity;
import ru.tsu.hits.springdb2.entity.UserEntity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;

public class UserDtoConverter {

    public static byte[] digest(byte[] input, String algorithm) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        byte[] result = md.digest(input);
        return result;
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static UserEntity convertDtoToEntity(CreateUpdateUserDto createUpdateUserDto){
        UserEntity userEntity = new UserEntity();
        byte[] shaInBytes = digest(createUpdateUserDto.getPassword().getBytes(UTF_8),"SHA-256");

        userEntity.setUuid(UUID.randomUUID().toString());
        userEntity.setCreationDate(createUpdateUserDto.getCreationDate());
        userEntity.setEditDate(createUpdateUserDto.getEditDate());
        userEntity.setEmail(createUpdateUserDto.getEmail());
        userEntity.setFullName(createUpdateUserDto.getFullName());
        userEntity.setPassHash(bytesToHex(shaInBytes));
        userEntity.setRole(createUpdateUserDto.getRole());

        return userEntity;
    }

    public static UserDto convertEntityToDto(UserEntity userEntity){
        UserDto userDto = new UserDto();

        userDto.setId(userEntity.getUuid());
        userDto.setCreationDate(userEntity.getCreationDate());
        userDto.setEditDate(userEntity.getEditDate());
        userDto.setEmail(userEntity.getEmail());
        userDto.setFullName(userEntity.getFullName());
        userDto.setPassword(userEntity.getPassHash());
        userDto.setRole(userEntity.getRole());
//        userDto.setCreatedTasks(TaskDtoConverter.convertTasksToDto(createdTasksE));
//        userDto.setEditedTasks(TaskDtoConverter.convertTasksToDto(editedTasksE));
//        userDto.setComments(CommentDtoConverter.convertCommentsToDto(commentsE));

        return userDto;
    }






}
