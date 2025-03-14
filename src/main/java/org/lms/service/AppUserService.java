package org.lms.service;

import org.lms.entity.User.AppUser;
import org.lms.entity.UserRole;
import org.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService implements UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean existsById(Long userId){
        return userRepository.existsById(userId);
    }
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
            AppUser oldUser = userRepository.findById(id).get();
            if (user.getFirstName() != null){
                oldUser.setFirstName(user.getFirstName());
            }
            if (user.getLastName() != null){
                oldUser.setLastName(user.getLastName());
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

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }
}