package org.lms.repository;

import org.lms.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<AppUser, Long> {
}
