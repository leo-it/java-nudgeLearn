package com.nudgeLearn.demo.dto;

public record AnswerResponse(
		boolean correct, Integer correctOptionIndex, String explanation, boolean quizCompleted) {
}
