package org.lms.repository;

import org.lms.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.expression.spel.ast.Assign;

public interface AssignmentRepository extends JpaRepository<Assignment,Long> {
}
