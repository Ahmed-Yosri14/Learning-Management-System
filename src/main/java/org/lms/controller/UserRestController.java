package org.lms.controller;


import org.lms.AuthorizationManager;
import org.lms.entity.AppUser;
import org.lms.entity.UserRole;
import org.lms.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user/")
public class UserRestController {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private AuthorizationManager authorizationManager;

    // all
    @GetMapping("/me")
    public ResponseEntity<AppUser> getProfile(){
        
        return ResponseEntity.ok(authorizationManager.getCurrentUser());
    }
    // all
    @PatchMapping("/me")
    public ResponseEntity<String> updateProfile(@RequestBody AppUser user){
        if (appUserService.update(authorizationManager.getCurrentUserId(), user)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }
    // all
    @DeleteMapping("/me")
    public ResponseEntity<String> deleteProfile(){
        if (appUserService.delete(authorizationManager.getCurrentUserId())){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }

    // admin
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("")
    public ResponseEntity<String> create(@RequestBody AppUser user) {
        if (appUserService.create(user)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");

    }
    // admin
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("{id}/")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody AppUser user) {
        if (appUserService.update(id, user)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }
    // admin
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}/")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        if (appUserService.delete(id)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }
    // admin
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{id}/")
    public ResponseEntity<AppUser> getById(@PathVariable("id") Long id) {
        AppUser user = appUserService.getById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
    // admin
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public List<AppUser> getAll(@RequestParam(required = false) UserRole role) {
        return appUserService.getAll(role);
    }
}