package org.lms.controller;

import org.lms.AuthorizationManager;
import org.lms.entity.Notification;
import org.lms.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/notification/")
public class NotificationRestController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AuthorizationManager authorizationManager;

    // all
    @GetMapping("")
    List<Notification> getAll(@RequestParam(required = false, defaultValue = "false") Boolean onlyUnread){
        return notificationService.getAllByUserId(authorizationManager.getCurrentUserId(), onlyUnread);
    }
    // all
    @GetMapping("{id}/")
    Notification getById(@RequestParam Long id){
        return notificationService.getById(authorizationManager.getCurrentUserId(), id);
    }
}