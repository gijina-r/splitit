package com.money.splitit.dto;

import com.money.splitit.model.Group;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private Set<Long> groupIds;
    private HashSet<Group> groupNames;
}

