package org.lms.controller;

import org.lms.AuthorizationManager;
import org.lms.EntityMapper;
import org.lms.entity.Notification;
import org.lms.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/notification")
public class NotificationRestController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AuthorizationManager authorizationManager;

    @Autowired
    private EntityMapper entityMapper;

    // all
    @GetMapping("")
    ResponseEntity<Object> getAll(@RequestParam(required = false, defaultValue = "false") Boolean onlyUnread){
        return ResponseEntity.ok(
                entityMapper.map(
                        new ArrayList<>(
                                notificationService.getAllByUserId(authorizationManager.getCurrentUserId(), onlyUnread
                                )
                        )
                )
        );
    }
    // all
    @GetMapping("/{id}")
    Notification getById(@PathVariable Long id){
        return notificationService.getById(authorizationManager.getCurrentUserId(), id);
    }
}