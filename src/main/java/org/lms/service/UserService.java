package org.lms.service;

import org.lms.entity.User.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetailsService();

    AppUser getById(Long userId);
}