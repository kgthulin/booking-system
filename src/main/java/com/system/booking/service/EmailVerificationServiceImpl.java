package com.system.booking.service;

import org.springframework.stereotype.Service;

@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {

	@Override
	public boolean sendVerifyEmail(String email) {
		return true;
	}
}
