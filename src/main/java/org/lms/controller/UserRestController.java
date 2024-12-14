package org.lms.controller;


import org.lms.entity.AppUser;
import org.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserRestController {

    @Autowired
    private UserService userService;


    @PutMapping("/")
    public ResponseEntity<String> create(@RequestBody AppUser user) {
        if (userService.create(user)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");

    }
    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody AppUser user) {
        if (userService.update(id, user)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        if (userService.delete(id)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }
    @GetMapping("/{id}")
    public ResponseEntity<AppUser> getById(@PathVariable("id") Long id) {
        AppUser user = userService.getById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
    @GetMapping("/")
    public List<AppUser> getAll() {
        return userService.getAll();
    }
}