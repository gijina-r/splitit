package com.money.splitit.service;


import com.money.splitit.model.Expense;
import com.money.splitit.model.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SettlementService {

    public List<String> calculateSettlement(List<Expense> expenses) {

        System.out.println("Expenses List:"+expenses);
        Map<String, Double> netBalance = new HashMap<>();

        // Step 1: compute net balance per user
        for (Expense expense : expenses) {

            System.out.println("Amount:"+expense.getAmount() + "splitbetween"+expense.getSplitBetween());
            double splitAmount = expense.getAmount() / expense.getSplitBetween().size();
            System.out.println("splitAmount List:"+splitAmount);
            // subtract splitAmount from each participant
            for (User username : expense.getSplitBetween()) {
                netBalance.put(username.getUsername(), netBalance.getOrDefault(username.getUsername(), 0.0) - splitAmount);
                System.out.println("netBalance List6:"+netBalance);
            }
            System.out.println("netBalance List1:"+netBalance);
            // add full amount to payer
            String payer = expense.getPaidBy().getUsername();
            netBalance.put(payer, netBalance.getOrDefault(payer, 0.0) + expense.getAmount());
            System.out.println("netBalance List2:"+netBalance);
        }

        // Step 2: create settlements
        List<String> settlements = new ArrayList<>();
        List<Map.Entry<String, Double>> positive = netBalance.entrySet().stream()
                .filter(e -> e.getValue() > 0).toList();
        List<Map.Entry<String, Double>> negative = netBalance.entrySet().stream()
                .filter(e -> e.getValue() < 0).toList();
        System.out.println("positive List2:"+positive);
        System.out.println("negative List2:"+negative);
        int i = 0, j = 0;
        while (i < positive.size() && j < negative.size()) {
            Map.Entry<String, Double> creditor = positive.get(i);
            Map.Entry<String, Double> debtor = negative.get(j);

            double amount = Math.min(creditor.getValue(), -debtor.getValue());
            settlements.add(debtor.getKey() + " pays " + amount + " to " + creditor.getKey());
            System.out.println("settlements List2:"+settlements);

            creditor.setValue(creditor.getValue() - amount);
            debtor.setValue(debtor.getValue() + amount);

            if (creditor.getValue() == 0) i++;
            if (debtor.getValue() == 0) j++;
            System.out.println("final"+settlements);
        }

        return settlements;
    }

}
