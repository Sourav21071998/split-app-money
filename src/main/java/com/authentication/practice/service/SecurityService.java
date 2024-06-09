package com.authentication.practice.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface SecurityService {

	public String login(String username, String password, HttpServletRequest request, HttpServletResponse response);
	
}
