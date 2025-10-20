package com.money.splitit.dto.mapper;

import com.money.splitit.dto.ExpenseDTO;
import com.money.splitit.model.Expense;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {

    // Convert DTO to Entity
    public Expense toEntity(ExpenseDTO dto) {
        if (dto == null) return null;

        Expense entity = new Expense();
        entity.setId(dto.getId());
        entity.setDescription(dto.getDescription());
        entity.setAmount(dto.getAmount());
        entity.setDate(dto.getDate());
        entity.setPayerId(dto.getPayerId());
        entity.setGroup(dto.getGroup());
        entity.setPaidBy(dto.getPaidBy());
       entity.setSplitBetween(dto.getSplitBetween());

        return entity;
    }

    // Convert Entity to DTO
    public ExpenseDTO toDTO(Expense entity) {
        if (entity == null) return null;

        ExpenseDTO dto = new ExpenseDTO();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setAmount(entity.getAmount());
        dto.setDate(entity.getDate());
        dto.setPaidBy(entity.getPaidBy());
         dto.setGroup(entity.getGroup());
         dto.setPayerId(entity.getPayerId());
         dto.setSplitBetween(entity.getSplitBetween());

        return dto;
    }
}