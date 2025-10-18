package com.money.splitit.service;


import com.money.splitit.model.Expense;
import com.money.splitit.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SettlementService {

    public List<String> calculateSettlement(List<Expense> expenses) {
        Map<Long, Double> netBalance = new HashMap<>();

        // Step 1: compute net balance per user
        for (Expense expense : expenses) {
            double splitAmount = expense.getAmount() / expense.getSplitBetween().size();
            for (User u : expense.getSplitBetween()) {
                netBalance.put(u.getId(), netBalance.getOrDefault(u.getId(), 0.0) - splitAmount);
            }
            netBalance.put(expense.getPaidBy().getId(),
                    netBalance.getOrDefault(expense.getPaidBy().getId(), 0.0) + expense.getAmount());
        }

        // Step 2: minimize transactions
        List<Map.Entry<Long, Double>> creditors = netBalance.entrySet().stream()
                .filter(e -> e.getValue() > 0)
                .sorted(Map.Entry.comparingByValue())
                .toList();

        List<Map.Entry<Long, Double>> debtors = netBalance.entrySet().stream()
                .filter(e -> e.getValue() < 0)
                .sorted(Map.Entry.comparingByValue())
                .toList();

        List<String> settlements = new ArrayList<>();
        int i = 0, j = 0;

        while (i < debtors.size() && j < creditors.size()) {
            double debit = -debtors.get(i).getValue();
            double credit = creditors.get(j).getValue();
            double settledAmount = Math.min(debit, credit);

            settlements.add("User " + debtors.get(i).getKey() + " pays " + settledAmount +
                    " to User " + creditors.get(j).getKey());

            debtors.get(i).setValue(debtors.get(i).getValue() + settledAmount);
            creditors.get(j).setValue(creditors.get(j).getValue() - settledAmount);

            if (Math.abs(debtors.get(i).getValue()) < 0.01) i++;
            if (Math.abs(creditors.get(j).getValue()) < 0.01) j++;
        }

        return settlements;
    }
}
