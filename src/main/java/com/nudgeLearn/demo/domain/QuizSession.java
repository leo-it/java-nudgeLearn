package com.nudgeLearn.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "quiz_session")
@Getter
@Setter
@NoArgsConstructor
public class QuizSession {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, updatable = false)
	private Instant startedAt;

	private Instant completedAt;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private QuizSessionStatus status;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private AppUser user;

	@ManyToMany
	@JoinTable(
			name = "quiz_session_question",
			joinColumns = @JoinColumn(name = "quiz_session_id"),
			inverseJoinColumns = @JoinColumn(name = "question_id"))
	private List<Question> questions = new ArrayList<>();

	@PrePersist
	void onCreate() {
		if (startedAt == null) {
			startedAt = Instant.now();
		}
		if (status == null) {
			status = QuizSessionStatus.PENDING;
		}
	}
}
