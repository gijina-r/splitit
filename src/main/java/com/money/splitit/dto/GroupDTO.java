package com.money.splitit.dto;

import lombok.Data;

import java.util.Set;

@Data
public class GroupDTO {
    private Long id;
    private String name;
    private Set<Long> memberIds;
    private Set<String> memberNames;
}