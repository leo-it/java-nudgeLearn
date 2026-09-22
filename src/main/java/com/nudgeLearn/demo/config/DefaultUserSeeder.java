package com.nudgeLearn.demo.config;

import com.nudgeLearn.demo.domain.AppUser;
import com.nudgeLearn.demo.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultUserSeeder implements ApplicationRunner {

	private final AppUserRepository appUserRepository;

	@Override
	public void run(ApplicationArguments args) {
		if (appUserRepository.existsByEmail(DefaultUser.EMAIL)) {
			return;
		}

		AppUser user = new AppUser();
		user.setDisplayName(DefaultUser.DISPLAY_NAME);
		user.setEmail(DefaultUser.EMAIL);
		appUserRepository.save(user);
	}
}
