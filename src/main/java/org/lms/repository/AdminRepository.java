package org.lms.repository;

import org.lms.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdminRepository extends JpaRepository<Admin, Long> {
}
