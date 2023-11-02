package com.crud.api.service;
import java.util.List;

import com.crud.api.entity.Expense;

public interface ExpenseService {
    Expense getExpenseById(Long id);

    void deleteExpenseById(Long id);

    Expense saveExpenseDetails(Expense expense);

    Expense updateExpenseDetails(Long id, Expense expense);

    List<Expense> getAllExpenses();
    List<Expense> getAllExpensesByUserId();

    // Page <Expense> getAllExpenses(Pageable page);

}
