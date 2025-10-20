package com.money.splitit.dto.mapper;

import com.money.splitit.dto.ExpenseDTO;
import com.money.splitit.dto.GroupDTO;
import com.money.splitit.model.Expense;
import com.money.splitit.model.Group;
import com.money.splitit.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GroupMapper {
    private final ExpenseMapper expenseMapper;

    public GroupMapper(ExpenseMapper expenseMapper) {
        this.expenseMapper = expenseMapper;
    }

    // DTO → Entity
    public Group toEntity(GroupDTO dto) {
        if (dto == null) return null;

        Group entity = new Group();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setMembers(dto.getMembers());
        entity.setDescription(dto.getDescription());

        List<Expense> expenses = null;
        if (dto.getExpenses() != null) {
            expenses = dto.getExpenses()
                    .stream()
                    .map(expenseMapper::toEntity)
                    .collect(Collectors.toList());
        }
        entity.setExpenses(expenses);

        return entity;
    }
}