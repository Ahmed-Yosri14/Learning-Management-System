package org.lms.controller;


import jakarta.validation.Valid;
import org.lms.entity.*;
import org.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserRestController {

    @Autowired
    private UserService userService;

    @PutMapping("/")
    public String createUser(@RequestBody @Valid AppUser user, BindingResult result) {
        if (result.hasErrors()) {
            return result.getAllErrors().toString();
        }
        else {
            AppUser newUser;
            if (user.getRole() == Role.STUDENT){
                newUser = new Student();
            }
            else if (user.getRole() == Role.ADMIN){
                newUser = new Admin();
            }
            else {
                newUser = new Instructor();
            }
            newUser.setEmail(user.getEmail());
            newUser.setPassword(user.getPassword());
            newUser.setRole(user.getRole());
            newUser.setName(user.getName());
            if (userService.createUser(user)){
                return "USer created successfully";
            }
            else {
                return "User could not be created";
            }
        }
    }
    @PatchMapping("/{id}")
    public String updateCourse(@PathVariable("id") Long id, @Valid AppUser user, BindingResult result) {
        if (result.hasErrors()) {
            return result.getAllErrors().toString();
        }
        else {
            user.setId(id);
            if (userService.updateUser(user)){
                return "Course updated successfully!";
            }
            else {
                return "Course not found!";
            }
        }
    }
    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable("id") Long id) {
        if (userService.deleteUser(id)){
            return "Course deleted successfully!";
        }
        else {
            return "Course not found!";
        }
    }
    @GetMapping("/{id}")
    public AppUser getCourse(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }
    @GetMapping("/")
    public List<AppUser> getCourses() {
        return userService.getAllUsers();
    }
}
