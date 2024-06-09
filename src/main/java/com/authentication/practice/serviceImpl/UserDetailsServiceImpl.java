package com.authentication.practice.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.authentication.practice.entity.UserEntity;
import com.authentication.practice.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("Inside method of loadUserByUsername ");
		UserEntity user = userRepository.findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("User not found with email: "+username);
		}
		else
		return new User(user.getEmail(),user.getPassword(),user.getRoles());
	}
	
	

}
