package com.authentication.practice.serviceImpl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.authentication.practice.entity.RoleEntity;
import com.authentication.practice.entity.UserEntity;
import com.authentication.practice.model.UserModel;
import com.authentication.practice.repository.UserRepository;
import com.authentication.practice.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public UserModel registerUser(UserModel userModel) throws Exception {
		log.info("Inside method of registerUser of serviceImpl ");
		String email = userModel.getEmail();
		UserEntity duplicateUser = userRepository.findByEmail(email);
		if(duplicateUser!=null) {
			throw new Exception("Email Id already exists. Try with other email Id.");
		}
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userModel, userEntity);
		Set<RoleEntity> roles = new HashSet<>();
		RoleEntity role = new RoleEntity();
		role.setId(1L);
		roles.add(role);
		userEntity.setRoles(roles);
		userEntity.setPassword(passwordEncoder.encode(userModel.getPassword()));
		UserEntity savedResponse = userRepository.save(userEntity);
		UserModel savedModel = new UserModel();
		BeanUtils.copyProperties(savedResponse, savedModel);
		return savedModel;
	}

}
