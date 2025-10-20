package com.money.splitit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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




    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST},fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "payer_id", insertable = false, updatable = false)
    private Long payerId; // ✅ read-only mirror of foreign key

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payer_id")
    private User paidBy;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "expense_splits",
            joinColumns = @JoinColumn(name = "expense_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> splitBetween = new HashSet<>();
}