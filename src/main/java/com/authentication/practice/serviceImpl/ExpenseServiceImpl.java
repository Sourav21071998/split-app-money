package com.authentication.practice.serviceImpl;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.authentication.practice.entity.ExpenseEntity;
import com.authentication.practice.entity.UserEntity;
import com.authentication.practice.model.ExpenseModel;
import com.authentication.practice.model.ResponseModel;
import com.authentication.practice.repository.ExpenseRepository;
import com.authentication.practice.repository.UserRepository;
import com.authentication.practice.service.ExpenseService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public ExpenseModel addExpenses(final String username, final ExpenseModel expenseModel) throws Exception {
		log.info("Inside service impl of addExpenses");
		try {
			if (expenseModel.getUsername() == null || expenseModel.getExpenses() == null) {
				throw new Exception("Username and expenses field cannot be empty");
			}
			UserEntity user = userRepository.findByEmail(username);
			ExpenseEntity expenseEntity = new ExpenseEntity();
			BeanUtils.copyProperties(expenseModel, expenseEntity);
			expenseEntity.setUserId(user.getId());
			expenseEntity.setCreatedDate(LocalDate.now());
			ExpenseEntity savedResponse = expenseRepository.save(expenseEntity);
			ExpenseModel savedModel = new ExpenseModel();
			BeanUtils.copyProperties(savedResponse, savedModel);
			log.info("Saved model: {} ", savedModel);
			return savedModel;
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	@Override
	public ExpenseModel modifyExpenses(final Long id, final ExpenseModel expenseModel) throws Exception {
		log.info("Inside service impl of modifyExpenses");
		try {
			ExpenseEntity entity = expenseRepository.findById(id).orElseThrow(() -> new Exception("Invalid id: " + id));
			if (expenseModel.getUsername() == null || expenseModel.getExpenses() == null) {
				throw new Exception("Username and expenses field cannot be empty");
			}
			entity.setExpenseName(expenseModel.getExpenseName());
			entity.setUsername(expenseModel.getUsername());
			entity.setExpenses(expenseModel.getExpenses());
			ExpenseEntity savedEntity = expenseRepository.save(entity);
			ExpenseModel savedResponse = new ExpenseModel();
			BeanUtils.copyProperties(savedEntity, savedResponse);
			log.info("Saved model: {}", savedResponse);
			return savedResponse;
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	@Override
	public void deleteExpenses(final Long id) throws Exception {
		log.info("Inside service impl of delete expenses");
		try {
			ExpenseEntity expenseEntity = expenseRepository.findById(id).get();
			expenseRepository.delete(expenseEntity);
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	@Override
	public List<ExpenseModel> listAllExpenses(final String username) throws Exception {
		log.info("Inside service impl method of listAllExpenses");
		try {
			UserEntity userEntity = userRepository.findByEmail(username);
			List<ExpenseEntity> users = expenseRepository.findByUserId(userEntity.getId());
			List<ExpenseModel> models = new ArrayList<>();
			for (ExpenseEntity user : users) {
				ExpenseModel expenseModel = new ExpenseModel();
				BeanUtils.copyProperties(user, expenseModel);
				models.add(expenseModel);
			}
			return models;
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	@Override
	public String getPrincipalUser() {
		log.info("Inside service impl of getPrincipalUser");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = null;
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof UserDetails) {
				username = ((UserDetails) principal).getUsername();
			} else {
				username = principal.toString();
			}
		}
		return username;
	}

	public ResponseModel splitMoney(String username) {
		log.info("Inside service impl of split money app..");
		UserEntity user = userRepository.findByEmail(username);
		List<ExpenseEntity> expenseList = expenseRepository.findByUserId(user.getId());
		log.info("Expense List size: ()", expenseList.size());
		Collections.sort(expenseList, new UserIdComparatorServiceImpl());
		double sum = expenseList.stream().mapToDouble(ExpenseEntity::getExpenses).sum();
		expenseList.forEach(e -> System.out.println(e));
		log.info("Sum = {} ", sum);
		String result = "";
		double avg = (sum * 1.0) / (expenseList.size());
		log.info("Average: () ", avg);
		int i;
		int j = expenseList.size() - 1;
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.HALF_UP);
		//String formattedValue = df.format(sum);
		for (i = 0; i < j;) {
			log.info("Inside for loop");
			if (expenseList.get(i).getExpenses() >= avg && expenseList.get(j).getExpenses() <= avg) {
				double amt = avg - expenseList.get(j).getExpenses();
				if (expenseList.get(i).getExpenses() - amt >= avg) {
					log.info("Inside first loop");
					expenseList.get(j).setExpenses(amt + expenseList.get(j).getExpenses());
					expenseList.get(i).setExpenses(expenseList.get(i).getExpenses() - amt);
					System.out.println(expenseList.get(j).getUsername() + " will pay" + (amt) + " το "
							+ expenseList.get(i).getUsername());
					result = result + expenseList.get(j).getUsername() + " will pay " + df.format(amt) + " το "
							+ expenseList.get(i).getUsername() + ".";
					j--;
				} else if ((expenseList.get(i).getExpenses() - avg) + (expenseList.get(j).getExpenses()) <= avg) {
					log.info("Inside second if block");
					double a = expenseList.get(i).getExpenses() - avg;
					expenseList.get(j).setExpenses(a + expenseList.get(j).getExpenses());
					expenseList.get(i).setExpenses(expenseList.get(i).getExpenses() - a);
					System.out.println(expenseList.get(j).getUsername() + " will pay " + a + " to "
							+ expenseList.get(i).getUsername());
					result = result + expenseList.get(j).getUsername() + " will pay " + df.format(a) + " το "
							+ expenseList.get(i).getUsername() + ". ";
					i++;
				} else {
					log.info("Inside third loop");
					double amt2 = avg - expenseList.get(j).getExpenses();
					expenseList.get(j).setExpenses(expenseList.get(j).getExpenses() + amt2);
					expenseList.get(i).setExpenses(expenseList.get(i).getExpenses() - amt2);
					System.out.println(expenseList.get(j).getUsername() + " will pay " + amt2 + " to "
							+ expenseList.get(i).getUsername());
					result = result + expenseList.get(j).getUsername() + " will pay " + df.format(amt2) + " to "
							+ expenseList.get(i).getUsername() + ".";
				}
			} else
				break;
		}
		ResponseModel responseModel = new ResponseModel(null, result, HttpStatus.OK);
		return responseModel;
	}

}
