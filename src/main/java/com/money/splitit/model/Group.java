package com.money.splitit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "group_user")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private Set<User> members;



    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<Expense> expenses= new ArrayList<>();

    //@OneToMany(mappedBy = "group",fetch = FetchType.EAGER)
   // private Set<Expense> expense;

   /* @ElementCollection
    private List<String> members;

    */

    // Constructors, getters, and setters
}