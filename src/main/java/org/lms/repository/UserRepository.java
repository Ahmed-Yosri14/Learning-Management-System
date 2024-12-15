package org.lms.repository;

import org.lms.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<AppUser, Long> {
    @Query(value = "SELECT * FROM app_user WHERE role = :role", nativeQuery = true)
    List<AppUser> findAllByRole(@Param("role") String role);

    Optional<AppUser> findByEmail(String email);
}