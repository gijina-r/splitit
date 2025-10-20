package com.money.splitit.controller;

import com.money.splitit.model.Group;
import com.money.splitit.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @PostMapping
    public Mono<ResponseEntity<Group>> createGroup(@RequestBody Group group) {
        this.groupService.createGroup(group);
        return Mono.just(ResponseEntity.ok(group));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Group>> getGroupById(@PathVariable Long id) {
        Group group = groupService.getGroupById(id);
        if (group != null) {
            return Mono.just(ResponseEntity.ok(group));
        }
        return Mono.just(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Mono<ResponseEntity<List<Group>>> getAllGroups() {
        return Mono.just(ResponseEntity.ok(groupService.getAllGroups()));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Group>> updateGroup(@PathVariable Long id, @RequestBody Group group) {
        group.setId(id);
        return Mono.just(ResponseEntity.ok(groupService.updateGroup(group)));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return Mono.just(ResponseEntity.ok().build());
    }
}