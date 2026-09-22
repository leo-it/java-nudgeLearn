package com.nudgeLearn.demo.controller;

import com.nudgeLearn.demo.dto.TopicRequest;
import com.nudgeLearn.demo.dto.TopicResponse;
import com.nudgeLearn.demo.service.TopicService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/topics")
@RequiredArgsConstructor
public class TopicController {

	private final TopicService topicService;

	@GetMapping
	public List<TopicResponse> list() {
		return topicService.listTopics();
	}

	@GetMapping("/{id}")
	public TopicResponse get(@PathVariable Long id) {
		return topicService.getTopic(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TopicResponse create(@RequestBody TopicRequest request) {
		return topicService.createTopic(request);
	}
}
