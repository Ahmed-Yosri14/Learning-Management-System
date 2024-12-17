package org.lms.repository;

import org.lms.entity.CourseMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseMaterialRepository extends JpaRepository<CourseMaterial, Long> {
    @Query("SELECT l FROM CourseMaterial l WHERE l.course.id = :courseId")
    List<CourseMaterial> findAllByCourseId(Long courseId);
}
