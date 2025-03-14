package org.lms.controller;


import org.lms.AuthorizationManager;
import org.lms.EntityMapper;
import org.lms.entity.User.AppUser;
import org.lms.entity.UserRole;
import org.lms.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/user")
public class UserRestController {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private AuthorizationManager authorizationManager;

    @Autowired
    private EntityMapper entityMapper;

    // all
    @GetMapping("/me")
    public ResponseEntity<Object> getProfile(){
        return ResponseEntity.ok(entityMapper.map(authorizationManager.getCurrentUser()));
    }
    // all
    @PatchMapping("/me")
    public ResponseEntity<Object> updateProfile(@RequestBody AppUser user){
        if (appUserService.update(authorizationManager.getCurrentUserId(), user)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }
    // all
    @DeleteMapping("/me")
    public ResponseEntity<Object> deleteProfile(){
        if (appUserService.delete(authorizationManager.getCurrentUserId())){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }

    // admin
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Long id, @RequestBody AppUser user) {
        if (!appUserService.existsById(id)){
            return ResponseEntity.status(404).body("User not found");
        }
        if (appUserService.update(id, user)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }
    // admin
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        if (!appUserService.existsById(id)){
            return ResponseEntity.status(404).body("User not found");
        }
        if (appUserService.delete(id)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }
    // admin
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        AppUser user = appUserService.getById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entityMapper.map(user));
    }
    // admin
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public ResponseEntity<Object> getAll(@RequestParam(required = false) UserRole role) {
        return ResponseEntity.ok(entityMapper.map(new ArrayList<>(appUserService.getAll(role))));
    }
}