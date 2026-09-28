package com.nudgeLearn.demo.dto;

import java.util.List;

public record QuizQuestionResponse(Long id, String prompt, List<String> options, boolean answered) {
}
