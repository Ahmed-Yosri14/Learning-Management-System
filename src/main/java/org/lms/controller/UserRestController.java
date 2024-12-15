package org.lms.controller;


import org.lms.entity.AppUser;
import org.lms.entity.UserRole;
import org.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user/")
public class UserRestController {

    @Autowired
    private UserService userService;

    // all
    @GetMapping("/me")
    public ResponseEntity<AppUser> getProfile(){
        return ResponseEntity.ok(userService.getById(1L));
    }
    // all
    @PatchMapping("/me")
    public ResponseEntity<String> updateProfile(@RequestBody AppUser user){
        if (userService.update(1L, user)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }
    // all
    @DeleteMapping("/me")
    public ResponseEntity<String> deleteProfile(){
        if (userService.delete(1L)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }

    // admin
    @PutMapping("")
    public ResponseEntity<String> create(@RequestBody AppUser user) {
        if (userService.create(user)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");

    }
    // admin
    @PatchMapping("{id}/")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody AppUser user) {
        if (userService.update(id, user)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }
    // admin
    @DeleteMapping("{id}/")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        if (userService.delete(id)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }
    // admin
    @GetMapping("{id}/")
    public ResponseEntity<AppUser> getById(@PathVariable("id") Long id) {
        AppUser user = userService.getById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
    // admin
    @GetMapping("")
    public List<AppUser> getAll(@RequestParam(required = false) UserRole role) {
        return userService.getAll(role);
    }
}