package com.nudgeLearn.demo.repository;

import com.nudgeLearn.demo.domain.AppUser;
import com.nudgeLearn.demo.domain.Topic;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {

	List<Topic> findByUserOrderByCreatedAtDesc(AppUser user);

	Optional<Topic> findByIdAndUser(Long id, AppUser user);
}
