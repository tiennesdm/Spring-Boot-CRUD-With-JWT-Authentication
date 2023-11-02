package com.crud.api.controller;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.crud.api.entity.Expense;
import com.crud.api.response.SuccessObject;
import com.crud.api.service.ExpenseService;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/myexpense")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/expense")
    public Object saveExpenseDetails(@Valid @RequestBody Expense expense) {

        Expense expenseDetails = expenseService.saveExpenseDetails(expense);
        return new SuccessObject(201, expenseDetails, "Data Created Successfully");
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/expenses")
    public Object getAllExpenses() {
        List<Expense> expenseDetails = expenseService.getAllExpenses();
        return new SuccessObject(201, expenseDetails, "Data Fetched Successfully");

    }
      @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/expensesByUserId")
    public Object getAllExpensesByUserId() {
        List<Expense> expenseDetails = expenseService.getAllExpensesByUserId();
        return new SuccessObject(201, expenseDetails, "Data Created Successfully");

    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/expense/{id}")
    public Object getExpenseById(@PathVariable Long id) {
        Expense expenseDetails = expenseService.getExpenseById(id);
        return new SuccessObject(201, expenseDetails, "Data Fetched Successfully");
    }

    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping("/expense/{id}")
    public Object deleteExpenseById(@PathVariable Long id) {
        expenseService.deleteExpenseById(id);
        return new SuccessObject(200, null, "Deleted Successfully");
    }

    @ResponseStatus(value = HttpStatus.ACCEPTED)
    @PutMapping(value = "expense/{id}")
    public Object updateExpenseById(@PathVariable Long id, @RequestBody Expense expense) {
        Expense expenseDetails = expenseService.updateExpenseDetails(id, expense);
          return new SuccessObject(200, expenseDetails, "Data Updated Successfully");
    }

}
