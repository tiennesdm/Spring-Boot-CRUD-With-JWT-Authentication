package com.crud.api.repository;
import java.util.List;

import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.crud.api.entity.Expense;



public interface ExpenseRepository extends JpaRepository<Expense, Long> {
	List<Expense> findByUserId(Long userId);

    	//SELECT * FROM tbl_expenses WHERE name LIKE '%keyword%'
	// Page<Expense> findByNameContaining(String keyword, Pageable page);
	
	
	// //SELECT * FROM tbl_expenses WHERE date BETWEEN 'startDate' AND 'endDate'
	// Page<Expense> findByDateBetween(Date startDate, Date endDate, Pageable page);
    
}
