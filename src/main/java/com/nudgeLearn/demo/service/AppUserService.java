package com.nudgeLearn.demo.service;

import com.nudgeLearn.demo.config.DefaultUser;
import com.nudgeLearn.demo.domain.AppUser;
import com.nudgeLearn.demo.dto.UserResponse;
import com.nudgeLearn.demo.exception.ResourceNotFoundException;
import com.nudgeLearn.demo.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AppUserService {

	private final AppUserRepository appUserRepository;

	@Transactional(readOnly = true)
	public AppUser requireDefaultUser() {
		return appUserRepository
				.findByEmail(DefaultUser.EMAIL)
				.orElseThrow(() -> new ResourceNotFoundException("El usuario por defecto no está sembrado"));
	}

	@Transactional(readOnly = true)
	public UserResponse getCurrentUser() {
		AppUser user = requireDefaultUser();
		return new UserResponse(user.getId(), user.getDisplayName(), user.getEmail());
	}
}
