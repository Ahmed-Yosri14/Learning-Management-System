package org.lms.service;

import org.lms.entity.Assignment;
import org.lms.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class AssignmentService {
    @Autowired
    private AssignmentRepository assignmentRepository;
    public Assignment createAssignment(Assignment assignment)
    {
        return assignmentRepository.save(assignment);
    }

    public Assignment getAssignment(Long id)
    {
        return assignmentRepository.findById(id).get();
    }

    public List<Assignment> getAssignmentsByCourseId(Long courseId)
    {
        return assignmentRepository.findAll();
    }
}
