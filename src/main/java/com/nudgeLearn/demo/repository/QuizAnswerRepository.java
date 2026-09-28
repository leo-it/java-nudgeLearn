package com.nudgeLearn.demo.repository;

import com.nudgeLearn.demo.domain.Question;
import com.nudgeLearn.demo.domain.QuizAnswer;
import com.nudgeLearn.demo.domain.QuizSession;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizAnswerRepository extends JpaRepository<QuizAnswer, Long> {

	boolean existsByQuizSessionAndQuestion(QuizSession quizSession, Question question);

	long countByQuizSession(QuizSession quizSession);

	List<QuizAnswer> findByQuizSession(QuizSession quizSession);
}
