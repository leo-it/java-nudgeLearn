package com.nudgeLearn.demo.repository;

import com.nudgeLearn.demo.domain.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {
}
