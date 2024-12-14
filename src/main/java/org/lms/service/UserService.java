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

    public boolean createUser(AppUser user) {
        try{
            userRepository.save(user);
            return true;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public boolean updateUser(AppUser user) {
        try {
            if (userRepository.existsById(user.getId())) {
                userRepository.save(user);
            }
            return true;
        }
        catch(Exception e){}
        return false;
    }
    public boolean deleteUser(Long id) {
        try {
            if (userRepository.existsById(id)) {
                userRepository.deleteById(id);
            }
            return true;
        }
        catch(Exception e){}
        return false;
    }
    public AppUser getUserById(Long id){
        return userRepository.findById(id).get();
    }
    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }
}
