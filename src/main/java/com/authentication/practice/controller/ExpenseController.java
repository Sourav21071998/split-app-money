package com.authentication.practice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.practice.model.ExpenseModel;
import com.authentication.practice.model.ResponseModel;
import com.authentication.practice.serviceImpl.ExpenseServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/expenses")
public class ExpenseController {

	@Autowired
	private ExpenseServiceImpl expenseServiceImpl;

	@PostMapping("/add")
	public ResponseEntity<?> addExpenses(@RequestBody ExpenseModel expenseModel) {
		log.info("Inside controller of addExpenses");
		try {
			String principalUser = expenseServiceImpl.getPrincipalUser();
			ExpenseModel addExpenses = expenseServiceImpl.addExpenses(principalUser,expenseModel);
			ResponseModel responseModel = new ResponseModel(addExpenses,
					"Expense details added for user: " + expenseModel.getUsername(), HttpStatus.CREATED);
			return new ResponseEntity<>(responseModel, HttpStatus.CREATED);
		} catch (Exception ex) {
			log.error("Error: {}", ex.getMessage());
			ResponseModel responseModel = new ResponseModel("Error occurred!", ex.getMessage(), HttpStatus.BAD_REQUEST);
			return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/modify")
	public ResponseEntity<?> modifyExpenses(@RequestParam Long id, @RequestBody ExpenseModel expenseModel) {
		log.info("Inside controller of modifyExpenses");
		try {
			ExpenseModel addExpenses = expenseServiceImpl.modifyExpenses(id, expenseModel);
			ResponseModel responseModel = new ResponseModel(addExpenses,
					"Expense details modified for user: " + expenseModel.getUsername(), HttpStatus.OK);
			return new ResponseEntity<>(responseModel, HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Exception: {} ", ex.getMessage());
			ResponseModel responseModel = new ResponseModel("Error occurred!", ex.getMessage(), HttpStatus.BAD_REQUEST);
			return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteExpenses(@RequestParam Long id){
		log.info("Inside controller of deleteExpenses");
		try {
			expenseServiceImpl.deleteExpenses(id);
			ResponseModel responseModel = new ResponseModel("Deleted Successfully","Deleted successfully",HttpStatus.OK);
			return new ResponseEntity<>(responseModel,HttpStatus.OK);
		}catch(Exception ex) {
			log.error("Exception: {} ",ex.getMessage());
			ResponseModel responseModel = new ResponseModel("Error Occurred!",ex.getMessage(),HttpStatus.BAD_REQUEST);
			return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("/list")
	public ResponseEntity<?> listAllExpenses(){
		ResponseModel responseModel = null;
		log.info("Inside controller of listAllExpenses");
		try {
			String principalUser = expenseServiceImpl.getPrincipalUser();
			List<ExpenseModel> listAllExpenses = expenseServiceImpl.listAllExpenses(principalUser);
			responseModel = new ResponseModel(listAllExpenses,"Success",HttpStatus.OK);
			return new ResponseEntity<>(responseModel,HttpStatus.OK);
		}catch(Exception ex) {
			log.error("Error: {} ",ex.getMessage());
			responseModel = new ResponseModel("Error occurred!",ex.getMessage(),HttpStatus.BAD_REQUEST);
			return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
		}		
	}
	
	@GetMapping("/split")
	public ResponseEntity<?> split(){
		ResponseModel responseModel = null;
		log.info("Inside controller of split");
		try {
			String principalUser = expenseServiceImpl.getPrincipalUser();
			 ResponseModel splitMoney = expenseServiceImpl.splitMoney(principalUser);
			responseModel = new ResponseModel(splitMoney,"Success",HttpStatus.OK);
			return new ResponseEntity<>(responseModel,HttpStatus.OK);
		}catch(Exception ex) {
			log.error("Error: {} ",ex.getMessage());
			responseModel = new ResponseModel("Error occurred!",ex.getMessage(),HttpStatus.BAD_REQUEST);
			return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
		}		
	}

}
