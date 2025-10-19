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
        Map<String, Double> netBalance = new HashMap<>();

        // Step 1: compute net balance per user
        for (Expense expense : expenses) {
            //double splitAmount = expense.getAmount() / expense.getSplitBetween().size();

            // subtract splitAmount from each participant
           // for (String username : expense.getSplitBetween()) {
              //  netBalance.put(username, netBalance.getOrDefault(username, 0.0) - splitAmount);
            //}

            // add full amount to payer
            User payer = expense.getPaidBy();
            netBalance.put(payer.getUsername(), netBalance.getOrDefault(payer.getUsername(), 0.0) + expense.getAmount());
        }

        // Step 2: create settlements
        List<String> settlements = new ArrayList<>();
        List<Map.Entry<String, Double>> positive = netBalance.entrySet().stream()
                .filter(e -> e.getValue() > 0).toList();
        List<Map.Entry<String, Double>> negative = netBalance.entrySet().stream()
                .filter(e -> e.getValue() < 0).toList();

        int i = 0, j = 0;
        while (i < positive.size() && j < negative.size()) {
            Map.Entry<String, Double> creditor = positive.get(i);
            Map.Entry<String, Double> debtor = negative.get(j);

            double amount = Math.min(creditor.getValue(), -debtor.getValue());
            settlements.add(debtor.getKey() + " pays " + amount + " to " + creditor.getKey());

            creditor.setValue(creditor.getValue() - amount);
            debtor.setValue(debtor.getValue() + amount);

            if (creditor.getValue() == 0) i++;
            if (debtor.getValue() == 0) j++;
        }

        return settlements;
    }

}
