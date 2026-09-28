package com.nudgeLearn.demo.service;

import com.nudgeLearn.demo.domain.AppUser;
import com.nudgeLearn.demo.domain.Topic;
import com.nudgeLearn.demo.dto.TopicRequest;
import com.nudgeLearn.demo.dto.TopicResponse;
import com.nudgeLearn.demo.exception.ResourceNotFoundException;
import com.nudgeLearn.demo.repository.TopicRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TopicService {

	private final TopicRepository topicRepository;
	private final AppUserService appUserService;

	@Transactional(readOnly = true)
	public List<TopicResponse> listTopics() {
		AppUser user = appUserService.requireDefaultUser();
		return topicRepository.findByUserOrderByCreatedAtDesc(user).stream()
				.map(this::toResponse)
				.toList();
	}

	@Transactional(readOnly = true)
	public TopicResponse getTopic(Long id) {
		return toResponse(requireOwnedTopic(id));
	}

	@Transactional(readOnly = true)
	public Topic requireOwnedTopic(Long id) {
		AppUser user = appUserService.requireDefaultUser();
		return topicRepository
				.findByIdAndUser(id, user)
				.orElseThrow(() -> new ResourceNotFoundException("Tema no encontrado: " + id));
	}

	@Transactional
	public TopicResponse createTopic(TopicRequest request) {
		if (request.title() == null || request.title().isBlank()) {
			throw new IllegalArgumentException("El título del tema es obligatorio");
		}

		AppUser user = appUserService.requireDefaultUser();
		Topic topic = new Topic();
		topic.setTitle(request.title().trim());
		topic.setDescription(request.description() == null ? null : request.description().trim());
		topic.setUser(user);
		return toResponse(topicRepository.save(topic));
	}

	private TopicResponse toResponse(Topic topic) {
		return new TopicResponse(topic.getId(), topic.getTitle(), topic.getDescription(), topic.getCreatedAt());
	}
}
