package com.nudgeLearn.demo.dto;

import com.nudgeLearn.demo.domain.QuizSessionStatus;
import java.util.List;

public record QuizResponse(
		Long id, Long topicId, QuizSessionStatus status, List<QuizQuestionResponse> questions) {
}
