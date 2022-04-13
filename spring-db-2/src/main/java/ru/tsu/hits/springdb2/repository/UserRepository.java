package ru.tsu.hits.springdb2.repository;

import org.springframework.data.repository.CrudRepository;
import ru.tsu.hits.springdb2.entity.Role;
import ru.tsu.hits.springdb2.entity.UserEntity;

import java.util.Date;
import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity,String> {

    List<UserEntity> findAllByCreationDateAndEditDateAndFullNameAndEmailAndUuidAndRole(Date cD, Date eD,
        String fullName, String email, String uuid, Role role);

}
