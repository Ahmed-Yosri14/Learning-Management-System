package org.lms.service;

import org.lms.entity.AppUser;
import org.lms.entity.UserRole;
import org.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

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

    private final PasswordEncoder encoder;
    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.userRepository = repository;
        this.encoder = encoder;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> userDetail = userRepository.findByName(username);
        // Converting userDetail to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }
    public String addUser(AppUser userInfo) {
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        userRepository.save(userInfo);
        return "User Added Successfully";
    }
}
