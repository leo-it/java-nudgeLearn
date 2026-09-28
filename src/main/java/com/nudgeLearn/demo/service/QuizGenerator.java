package com.nudgeLearn.demo.service;

import com.nudgeLearn.demo.domain.Topic;
import com.nudgeLearn.demo.dto.GeneratedQuestion;
import java.util.List;

public interface QuizGenerator {

	List<GeneratedQuestion> generate(Topic topic, int count);
}
