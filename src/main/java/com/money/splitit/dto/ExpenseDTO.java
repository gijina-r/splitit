package com.money.splitit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.money.splitit.model.User;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ExpenseDTO {
    private Long id;
    private String description;
    private Double amount;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime date;

    private User payer;
    private String group;

    private String paidBy;
    private List<String> splitBetween;
}