package com.nudgeLearn.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class QuizFlowTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void createQuizAndAnswerWrongShowsExplanation() throws Exception {
		MvcResult topicResult = mockMvc.perform(post("/api/topics")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"title\":\"Inglés\",\"description\":\"Vocabulario\"}"))
				.andExpect(status().isCreated())
				.andReturn();
		Integer topicId = JsonPath.read(topicResult.getResponse().getContentAsString(), "$.id");

		MvcResult quizResult = mockMvc.perform(post("/api/topics/" + topicId + "/quizzes"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.questions.length()").value(5))
				.andExpect(jsonPath("$.questions[0].correctOptionIndex").doesNotExist())
				.andExpect(jsonPath("$.questions[0].explanation").doesNotExist())
				.andReturn();

		String quizBody = quizResult.getResponse().getContentAsString();
		Integer quizId = JsonPath.read(quizBody, "$.id");
		Integer questionId = JsonPath.read(quizBody, "$.questions[0].id");

		mockMvc.perform(post("/api/quizzes/" + quizId + "/answers")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"questionId\":" + questionId + ",\"selectedOptionIndex\":3}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.correct").value(false))
				.andExpect(jsonPath("$.explanation").isNotEmpty())
				.andExpect(jsonPath("$.correctOptionIndex").exists());

		mockMvc.perform(get("/api/quizzes/" + quizId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.questions[0].answered").value(true));
	}
}
