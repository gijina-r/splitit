package com.money.splitit.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Double amount;
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "payer_id")
    private User payer;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;


    //@ManyToOne
    @ManyToOne
    @JoinColumn(name = "paid_by_id")
    private User paidBy;
    /*@ManyToMany
    @JoinTable(
            name = "settlements",
            joinColumns = @JoinColumn(name = "expense_id"),
            inverseJoinColumns = @JoinColumn(name = "payer_id")
    )
    private List<String> splitBetween;*/
}