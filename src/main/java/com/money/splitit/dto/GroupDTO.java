package com.money.splitit.dto;

import com.money.splitit.model.Expense;
import com.money.splitit.model.User;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class GroupDTO {
    private Long id;
    private String name;
    private String description;
    private Set<User> members;
    private Set<ExpenseDTO> expenses;
}