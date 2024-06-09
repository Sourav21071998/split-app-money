package com.authentication.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authentication.practice.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	UserEntity findByEmail(String email);

}
