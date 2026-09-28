package com.nudgeLearn.demo.service;

import com.nudgeLearn.demo.domain.AppUser;
import com.nudgeLearn.demo.domain.Question;
import com.nudgeLearn.demo.domain.QuizAnswer;
import com.nudgeLearn.demo.domain.QuizSession;
import com.nudgeLearn.demo.domain.QuizSessionStatus;
import com.nudgeLearn.demo.domain.Topic;
import com.nudgeLearn.demo.dto.AnswerRequest;
import com.nudgeLearn.demo.dto.AnswerResponse;
import com.nudgeLearn.demo.dto.GeneratedQuestion;
import com.nudgeLearn.demo.dto.QuizQuestionResponse;
import com.nudgeLearn.demo.dto.QuizResponse;
import com.nudgeLearn.demo.exception.ResourceNotFoundException;
import com.nudgeLearn.demo.repository.QuestionRepository;
import com.nudgeLearn.demo.repository.QuizAnswerRepository;
import com.nudgeLearn.demo.repository.QuizSessionRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuizService {

	static final int DEFAULT_QUESTION_COUNT = 5;

	private final AppUserService appUserService;
	private final TopicService topicService;
	private final QuizGenerator quizGenerator;
	private final QuestionRepository questionRepository;
	private final QuizSessionRepository quizSessionRepository;
	private final QuizAnswerRepository quizAnswerRepository;
	private final ProgressService progressService;

	@Transactional
	public QuizResponse createQuiz(Long topicId) {
		AppUser user = appUserService.requireDefaultUser();
		Topic topic = topicService.requireOwnedTopic(topicId);

		List<Question> questions = quizGenerator.generate(topic, DEFAULT_QUESTION_COUNT).stream()
				.map(generated -> toQuestion(generated, topic))
				.toList();
		questionRepository.saveAll(questions);

		QuizSession session = new QuizSession();
		session.setUser(user);
		session.setStatus(QuizSessionStatus.PENDING);
		session.setQuestions(new ArrayList<>(questions));
		return toResponse(quizSessionRepository.save(session));
	}

	@Transactional(readOnly = true)
	public QuizResponse getQuiz(Long quizId) {
		return toResponse(requireOwnedSession(quizId));
	}

	@Transactional
	public AnswerResponse answer(Long quizId, AnswerRequest request) {
		if (request.questionId() == null || request.selectedOptionIndex() == null) {
			throw new IllegalArgumentException("questionId y selectedOptionIndex son obligatorios");
		}

		QuizSession session = requireOwnedSession(quizId);
		if (session.getStatus() != QuizSessionStatus.PENDING) {
			throw new IllegalArgumentException("Este quiz ya está completado");
		}

		Question question = session.getQuestions().stream()
				.filter(q -> q.getId().equals(request.questionId()))
				.findFirst()
				.orElseThrow(() -> new ResourceNotFoundException("La pregunta no pertenece a este quiz"));

		if (request.selectedOptionIndex() < 0
				|| request.selectedOptionIndex() >= question.getOptions().size()) {
			throw new IllegalArgumentException("La opción elegida no es válida");
		}

		if (quizAnswerRepository.existsByQuizSessionAndQuestion(session, question)) {
			throw new IllegalArgumentException("Esa pregunta ya fue respondida");
		}

		boolean correct = request.selectedOptionIndex().equals(question.getCorrectOptionIndex());

		QuizAnswer answer = new QuizAnswer();
		answer.setQuizSession(session);
		answer.setQuestion(question);
		answer.setSelectedOptionIndex(request.selectedOptionIndex());
		answer.setCorrect(correct);
		quizAnswerRepository.save(answer);

		progressService.applyAnswer(session.getUser(), question.getTopic(), correct);

		boolean completed = quizAnswerRepository.countByQuizSession(session) >= session.getQuestions().size();
		if (completed) {
			session.setStatus(QuizSessionStatus.COMPLETED);
			session.setCompletedAt(Instant.now());
		}

		if (correct) {
			return new AnswerResponse(true, null, null, completed);
		}
		return new AnswerResponse(false, question.getCorrectOptionIndex(), question.getExplanation(), completed);
	}

	private QuizSession requireOwnedSession(Long quizId) {
		AppUser user = appUserService.requireDefaultUser();
		return quizSessionRepository
				.findByIdAndUser(quizId, user)
				.orElseThrow(() -> new ResourceNotFoundException("Quiz no encontrado: " + quizId));
	}

	private Question toQuestion(GeneratedQuestion generated, Topic topic) {
		Question question = new Question();
		question.setPrompt(generated.prompt());
		question.setOptions(new ArrayList<>(generated.options()));
		question.setCorrectOptionIndex(generated.correctOptionIndex());
		question.setExplanation(generated.explanation());
		question.setTopic(topic);
		return question;
	}

	private QuizResponse toResponse(QuizSession session) {
		Set<Long> answeredIds = new HashSet<>();
		for (QuizAnswer answer : quizAnswerRepository.findByQuizSession(session)) {
			answeredIds.add(answer.getQuestion().getId());
		}

		List<QuizQuestionResponse> questions = session.getQuestions().stream()
				.map(question -> new QuizQuestionResponse(
						question.getId(),
						question.getPrompt(),
						question.getOptions(),
						answeredIds.contains(question.getId())))
				.toList();

		Long topicId = session.getQuestions().isEmpty() ? null : session.getQuestions().get(0).getTopic().getId();
		return new QuizResponse(session.getId(), topicId, session.getStatus(), questions);
	}
}
