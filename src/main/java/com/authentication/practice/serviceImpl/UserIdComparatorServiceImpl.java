package com.authentication.practice.serviceImpl;

import java.util.Comparator;

import com.authentication.practice.entity.ExpenseEntity;

public class UserIdComparatorServiceImpl implements Comparator<ExpenseEntity>{

	@Override
	public int compare(ExpenseEntity o1, ExpenseEntity o2) {
		return Double.compare(o2.getExpenses(), o1.getExpenses());
	}

}
