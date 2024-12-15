package org.lms.service;

import org.lms.entity.AppUser;
import org.lms.entity.UserRole;
import org.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public boolean create(AppUser user) {
        try{
            userRepository.save(user);
            return true;
        }
        catch(Exception e){}
        return false;
    }
    public boolean update(Long id, AppUser user) {
        try {
            var oldUser = userRepository.findById(id).get();
            if (user.getName() != null){
                oldUser.setName(user.getName());
            }
            if (user.getEmail() != null){
                oldUser.setEmail(user.getEmail());
            }
            if (user.getPassword() != null){
                oldUser.setPassword(user.getPassword());
            }

            userRepository.save(oldUser);
            return true;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public boolean delete(Long id) {
        try {
            userRepository.deleteById(id);
            return true;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public AppUser getById(Long id) {
        return userRepository.findById(id).get();
    }
    public List<AppUser> getAll(UserRole userRole) {
        if (userRole == null) {
            return userRepository.findAll();
        }
        return userRepository.findAllByRole(userRole.toString());
    }

}