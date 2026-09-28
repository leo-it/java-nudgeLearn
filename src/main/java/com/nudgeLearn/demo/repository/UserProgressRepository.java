package com.nudgeLearn.demo.repository;

import com.nudgeLearn.demo.domain.AppUser;
import com.nudgeLearn.demo.domain.Topic;
import com.nudgeLearn.demo.domain.UserProgress;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {

	Optional<UserProgress> findByUserAndTopic(AppUser user, Topic topic);
}
