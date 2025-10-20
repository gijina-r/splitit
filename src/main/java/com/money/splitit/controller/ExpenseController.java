package com.money.splitit.controller;

import com.money.splitit.model.Expense;
import com.money.splitit.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public Mono<ResponseEntity<Expense>> createExpense(@RequestBody Expense expense) {
        return Mono.just(ResponseEntity.ok(expenseService.createExpense(expense)));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Expense>> getExpenseById(@PathVariable Long id) {
        Expense expense = expenseService.getExpenseById(id);
        if (expense != null) {
            return Mono.just(ResponseEntity.ok(expense));
        }
        return Mono.just(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Mono<ResponseEntity<List<Expense>>> getAllExpenses() {
        return Mono.just(ResponseEntity.ok(expenseService.getAllExpenses()));
    }

    @GetMapping("/group/{groupId}")
    public Mono<ResponseEntity<List<Expense>>> getExpensesByGroupId(@PathVariable Long groupId) {
        return Mono.just(ResponseEntity.ok(expenseService.getExpensesByGroupId(groupId)));
    }

    @GetMapping("/payer/{payerId}")
    public Mono<ResponseEntity<List<Expense>>> getExpensesByPayerId(@PathVariable Long payerId) {
        return Mono.just(ResponseEntity.ok(expenseService.getExpensesByPayerId(payerId)));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Expense>> updateExpense(@PathVariable Long id, @RequestBody Expense expense) {
        expense.setId(id);
        return Mono.just(ResponseEntity.ok(expenseService.updateExpense(expense)));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return Mono.just(ResponseEntity.ok().build());
    }
}