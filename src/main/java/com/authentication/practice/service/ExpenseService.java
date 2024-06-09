package com.authentication.practice.service;

import java.util.List;

import com.authentication.practice.model.ExpenseModel;

public interface ExpenseService {

	public ExpenseModel addExpenses(String username,ExpenseModel expenseModel)throws Exception;
	
	public ExpenseModel modifyExpenses(Long id, ExpenseModel expenseModel)throws Exception;
	
	public void deleteExpenses(Long id)throws Exception;
	
	public List<ExpenseModel> listAllExpenses(String username)throws Exception;
	
	public String getPrincipalUser();
}
