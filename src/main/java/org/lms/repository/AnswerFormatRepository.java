package org.lms.repository;

import org.lms.entity.Answer.AnswerFormat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerFormatRepository extends JpaRepository<AnswerFormat, Long> {
}
