package com.nudgeLearn.demo.controller;

import com.nudgeLearn.demo.dto.AnswerRequest;
import com.nudgeLearn.demo.dto.AnswerResponse;
import com.nudgeLearn.demo.dto.QuizResponse;
import com.nudgeLearn.demo.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizController {

	private final QuizService quizService;

	@GetMapping("/{id}")
	public QuizResponse get(@PathVariable Long id) {
		return quizService.getQuiz(id);
	}

	@PostMapping("/{id}/answers")
	public AnswerResponse answer(@PathVariable Long id, @RequestBody AnswerRequest request) {
		return quizService.answer(id, request);
	}
}
