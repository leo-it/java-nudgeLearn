package com.nudgeLearn.demo.dto;

import java.util.List;

public record GeneratedQuestion(
		String prompt, List<String> options, int correctOptionIndex, String explanation) {
}
