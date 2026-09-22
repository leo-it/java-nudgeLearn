package com.nudgeLearn.demo.dto;

import java.time.Instant;

public record TopicResponse(Long id, String title, String description, Instant createdAt) {
}
