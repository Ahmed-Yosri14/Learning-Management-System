package org.lms.service;

import org.lms.entity.AppUser;
import org.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    void createUser(AppUser user) {
        userRepository.save(user);
    }
    void updateUser(AppUser user) {
        if (userRepository.existsById(user.getId())){
            userRepository.save(user);
        }
    }
    void deleteUser(AppUser user) {
        if (userRepository.existsById(user.getId())){
            userRepository.deleteById(user.getId());
        }
    }
    AppUser getUserById(Long id){
        return userRepository.findById(id).get();
    }
    List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }
}
