package com.crud.api.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
// import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.crud.api.entity.Expense;
import com.crud.api.exceptions.ResourceNotFoundException;
import com.crud.api.repository.ExpenseRepository;

@Service
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepository;

	@Autowired
	private UserService userService;

	@Override
	public Expense getExpenseById(Long id) {
		try {
			Optional<Expense> expense = expenseRepository.findById(id);
			if (expense.isPresent()) {
				return expense.get();
			}
		} catch (Exception error) {
			throw new ResourceNotFoundException("Expense is not found for the id " + id);
		}
		return null;

	}

	@Override
	public void deleteExpenseById(Long id) {
		try {
			expenseRepository.deleteById(id);

		} catch (Exception error) {
			throw new ResourceNotFoundException("Expense is not found for the id " + id);
		}

	}

	@Override
	public Expense saveExpenseDetails(Expense expense) {
		try {
			expense.setUser(userService.getLoggedInUser());
			return expenseRepository.save(expense);
		} catch (Exception err) {
			throw new ResourceNotFoundException("Something went wrong");
		}

	}

	@Override
	public List<Expense> getAllExpenses() {
		try {
			return expenseRepository.findAll();
		} catch (Exception err) {
			throw new ResourceNotFoundException("Something went wrong in fetching");
		}

	}
	@Override
	public List<Expense> getAllExpensesByUserId() {
		try {
			return expenseRepository.findByUserId(userService.getLoggedInUser().getId());
		} catch (Exception err) {
			throw new ResourceNotFoundException("Something went wrong in fetching");
		}

	}

	@Override
	public Expense updateExpenseDetails(Long id, Expense expense) {
		try {
			Expense existingExpense = getExpenseById(id);
			existingExpense.setName(expense.getName() != null ? expense.getName() : existingExpense.getName());
			existingExpense.setDescription(
					expense.getDescription() != null ? expense.getDescription() : existingExpense.getDescription());
			existingExpense.setAmount(expense.getAmount() != null ? expense.getAmount() : existingExpense.getAmount());
			return expenseRepository.save(existingExpense);
		} catch (Exception error) {
			throw new ResourceNotFoundException("Something went wrong" + error.toString());
		}

	}

}
