package com.nudgeLearn.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
		name = "quiz_answer",
		uniqueConstraints = @UniqueConstraint(columnNames = {"quiz_session_id", "question_id"}))
@Getter
@Setter
@NoArgsConstructor
public class QuizAnswer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "quiz_session_id", nullable = false)
	private QuizSession quizSession;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "question_id", nullable = false)
	private Question question;

	@Column(nullable = false)
	private int selectedOptionIndex;

	@Column(nullable = false)
	private boolean correct;

	@Column(nullable = false, updatable = false)
	private Instant answeredAt;

	@PrePersist
	void onCreate() {
		answeredAt = Instant.now();
	}
}
