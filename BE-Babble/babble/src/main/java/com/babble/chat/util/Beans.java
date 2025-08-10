package com.babble.chat.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class Beans {

	@Bean
	SecureRandom secureRandom() throws NoSuchAlgorithmException {
		return SecureRandom.getInstanceStrong();
	}
}
