package com.authentication.practice.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseModel {
	
	private Long id;
	private String username;
	private String expenseName;
	private Double expenses;
	private LocalDate createdDate;
	private Long userId;

}
