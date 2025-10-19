package com.money.splitit.dto;

import com.money.splitit.model.Expense;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class GroupDTO {
    private Long id;
    private String name;
    private List<String> members;
    private Set<ExpenseDTO> expenses;
}