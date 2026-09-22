package com.nudgeLearn.demo.controller;

import com.nudgeLearn.demo.dto.UserResponse;
import com.nudgeLearn.demo.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private final AppUserService appUserService;

	@GetMapping("/me")
	public UserResponse me() {
		return appUserService.getCurrentUser();
	}
}
