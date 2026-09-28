package com.nudgeLearn.demo.repository;

import com.nudgeLearn.demo.domain.AppUser;
import com.nudgeLearn.demo.domain.QuizSession;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizSessionRepository extends JpaRepository<QuizSession, Long> {

	Optional<QuizSession> findByIdAndUser(Long id, AppUser user);
}
