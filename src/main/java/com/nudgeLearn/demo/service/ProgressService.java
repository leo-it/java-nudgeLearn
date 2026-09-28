package com.nudgeLearn.demo.service;

import com.nudgeLearn.demo.domain.AppUser;
import com.nudgeLearn.demo.domain.Topic;
import com.nudgeLearn.demo.domain.UserProgress;
import com.nudgeLearn.demo.repository.UserProgressRepository;
import java.time.Duration;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProgressService {

	private final UserProgressRepository userProgressRepository;

	public void applyAnswer(AppUser user, Topic topic, boolean correct) {
		UserProgress progress = userProgressRepository
				.findByUserAndTopic(user, topic)
				.orElseGet(() -> newProgress(user, topic));

		if (correct) {
			progress.setCorrectCount(progress.getCorrectCount() + 1);
			progress.setLeitnerBox(Math.min(5, progress.getLeitnerBox() + 1));
		} else {
			progress.setIncorrectCount(progress.getIncorrectCount() + 1);
			progress.setLeitnerBox(1);
		}

		progress.setLastReviewedAt(Instant.now());
		progress.setNextReviewAt(Instant.now().plus(Duration.ofDays(progress.getLeitnerBox())));
		userProgressRepository.save(progress);
	}

	private UserProgress newProgress(AppUser user, Topic topic) {
		UserProgress progress = new UserProgress();
		progress.setUser(user);
		progress.setTopic(topic);
		progress.setCorrectCount(0);
		progress.setIncorrectCount(0);
		progress.setLeitnerBox(1);
		return progress;
	}
}
