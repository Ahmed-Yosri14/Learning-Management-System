package org.lms.service;

import org.lms.entity.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetailsService();

    AppUser getById(Long userId);
}