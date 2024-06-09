package com.authentication.practice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authentication.practice.entity.ExpenseEntity;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long>{
	
	List<ExpenseEntity> findByUserId(Long id);

}
