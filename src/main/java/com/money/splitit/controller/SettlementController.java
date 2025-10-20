package com.money.splitit.controller;

import com.money.splitit.model.Expense;
import com.money.splitit.repository.ExpenseRepository;
import com.money.splitit.service.SettlementService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/settlement")
public class SettlementController {

    private final ExpenseRepository expenseRepository;
    private final SettlementService settlementService;

    public SettlementController(ExpenseRepository expenseRepository, SettlementService settlementService) {
        this.expenseRepository = expenseRepository;
        this.settlementService = settlementService;
    }

    @GetMapping("/group/{groupId}")
    public Mono<List<String>> getSettlement(@PathVariable Long groupId) {
        List<Expense> expenses = expenseRepository.findByGroupId(groupId);
        System.out.println("expenses details "+expenses);
        //return Mono.just(expenses);
        return Mono.just(settlementService.calculateSettlement(expenses));
    }
}
