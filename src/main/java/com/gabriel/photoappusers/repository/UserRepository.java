package com.gabriel.photoappusers.repository;

import com.gabriel.photoappusers.data.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);

}
