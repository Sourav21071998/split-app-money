package com.authentication.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.practice.model.ResponseModel;
import com.authentication.practice.model.UserModel;
import com.authentication.practice.security.JwtBlacklist;
import com.authentication.practice.service.SecurityService;
import com.authentication.practice.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtBlacklist jwtBlacklist;

	@Autowired
	private SecurityService securityService;

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody UserModel userModel) {
		try {
			log.info("Inside controller of registerUser");
			UserModel registerUser = userService.registerUser(userModel);
			ResponseModel responseModel = new ResponseModel("Registration successful",
					"Registration successful for user: " + registerUser.getEmail(), HttpStatus.CREATED);
			return new ResponseEntity<>(responseModel, HttpStatus.CREATED);
		} catch (Exception ex) {
			ResponseModel responseModel = new ResponseModel("Registration Unsuccessful",
					"Registration Unsuccessful:  " + ex.getMessage(), HttpStatus.BAD_REQUEST);
			return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody UserModel userModel, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("Inside controller of loginUser");
		ResponseModel responseModel = null;
		try {
			String jwtToken = securityService.login(userModel.getEmail(), userModel.getPassword(), request, response);
//			boolean loginUser = userService.loginUser(userModel,request,response);
			if (jwtToken != null) {
				responseModel = new ResponseModel("Token generation sucessful", jwtToken, HttpStatus.OK);
				return new ResponseEntity<>(responseModel, HttpStatus.OK);
			} else {
				responseModel = new ResponseModel("Login unsuccessful", "Login Unsuccessful", HttpStatus.UNAUTHORIZED);
				return new ResponseEntity<>(responseModel, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception ex) {
			responseModel = new ResponseModel("Login Unsuccessful", "Login Unsuccessful: " + ex.getMessage(),
					HttpStatus.UNAUTHORIZED);
			return new ResponseEntity<>(responseModel, HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/signout")
	public ResponseEntity<?> logout(@RequestHeader("Authorization") String authorizationHeader) {
		log.info("Inside controller of logout..{} ", authorizationHeader);
		ResponseModel responseModel = null;
		try {
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				String token = authorizationHeader.substring(7);
				jwtBlacklist.addToken(token);
				responseModel = new ResponseModel("Success", "Successfully logged out", HttpStatus.OK);
				return ResponseEntity.ok(responseModel);
			} else {
				responseModel = new ResponseModel("Error occurred!", "Invalid token", HttpStatus.BAD_REQUEST);
				return ResponseEntity.badRequest().body(responseModel);
			}
		} catch (Exception ex) {
			responseModel = new ResponseModel("Error occurred!", "Logout Unsuccessful: " + ex.getMessage(),
					HttpStatus.BAD_REQUEST);
			return ResponseEntity.badRequest().body(responseModel);
		}
	}

	@GetMapping("/get")
	public String helloWorld() {
		return "" + jwtBlacklist.getSize();
	}

}
