package com.money.splitit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.money.splitit.model.Group;
import com.money.splitit.model.User;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class ExpenseDTO {
    private Long id;
    private String description;
    private Double amount;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime date;


    private Group group;
    private Long payerId;
    private User paidBy;
    private Set<User> splitBetween;
}