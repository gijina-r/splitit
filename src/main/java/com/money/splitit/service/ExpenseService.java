package com.money.splitit.service;

import com.money.splitit.model.Expense;
import com.money.splitit.model.Group;
import com.money.splitit.model.User;
import com.money.splitit.repository.ExpenseRepository;
import com.money.splitit.repository.GroupRepository;
import com.money.splitit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;

    @Transactional
    public Expense createExpense(Expense expense) {
        if (expense.getSplitBetween() == null || expense.getSplitBetween().isEmpty()) {
            System.out.println("⚠No split users for expense: " + expense.getDescription());

        }

        System.out.println("expense::: " + expense.getSplitBetween());
        // Resolve group from DB and attach
        if (expense.getGroup() != null && expense.getGroup().getId() != null) {
            Group group = groupRepository.findById(expense.getGroup().getId())
                    .orElseThrow(() -> new RuntimeException("Group not found: " + expense.getGroup().getId()));
            expense.setGroup(group);
        } else {
            throw new RuntimeException("Group is required for creating an expense");
        }

        // Resolve users
        Set<User> splitUsers = expense.getSplitBetween().stream()
                .map(u -> userRepository.findById(u.getId())
                        .orElseThrow(() -> new RuntimeException("User not found: " + u.getId())))
                .collect(Collectors.toSet());
        expense.setSplitBetween(splitUsers);

        // Resolve payer
        User payer = userRepository.findById(expense.getPaidBy().getId())
                .orElseThrow(() -> new RuntimeException("Payer not found"));
        expense.setPaidBy(payer);

        // Save expense
        return expenseRepository.save(expense);
    }


    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id).orElse(null);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense updateExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    public List<Expense> getExpensesByGroupId(Long groupId) {
        return expenseRepository.findByGroupId(groupId);
    }

    public List<Expense> getExpensesByPayerId(Long payerId) {
        return expenseRepository.findByPayerId(payerId);
    }
}