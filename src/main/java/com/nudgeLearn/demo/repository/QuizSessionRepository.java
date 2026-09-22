package com.nudgeLearn.demo.repository;

import com.nudgeLearn.demo.domain.QuizSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizSessionRepository extends JpaRepository<QuizSession, Long> {
}
