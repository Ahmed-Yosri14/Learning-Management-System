package org.lms.service.Feedback;

import org.lms.entity.Feedback.QuizFeedback;
import org.lms.repository.QuizFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizFeedbackService extends FeedbackService {

    @Autowired
    QuizFeedbackRepository quizFeedbackRepository;

    public boolean existsById(Long courseId, Long quizId, Long quizSubmissionId, Long id) {
        try {
            if (!existsById(id)) {
                return false;
            }
            QuizFeedback quizFeedback = (QuizFeedback) getById(id);
            return quizFeedback != null &&
                    quizFeedback.getQuizSubmission().getId().equals(quizSubmissionId);
        } catch(Exception e) {
            return false;
        }
    }

    public QuizFeedback getById(Long courseId, Long quizId, Long quizSubmissionId, Long id) {
        try {
            if (!existsById(courseId, quizId, quizSubmissionId, id)) {
                return null;
            }
            return (QuizFeedback) getById(id);
        } catch(Exception e) {
            return null;
        }
    }

    public boolean create(QuizFeedback quizFeedback) {
        try {
            quizFeedbackRepository.save(quizFeedback);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public List<QuizFeedback> getAllByQuizSubmissionId(Long courseId, Long quizId, Long quizSubmissionId) {
        try {
            return quizFeedbackRepository.findAllByQuizSubmissionId(quizSubmissionId);
        } catch(Exception e) {
            return null;
        }
    }
}