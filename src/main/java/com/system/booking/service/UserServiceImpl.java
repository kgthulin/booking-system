package com.system.booking.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.system.booking.entity.AppUser;
import com.system.booking.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public Optional<AppUser> findUserById(Long id) {
		return this.userRepository.findById(id);
	}

	@Override
	public AppUser createUser(AppUser user) {
		return this.userRepository.save(user);
	}

	@Override
	public AppUser updateUser(AppUser user) {
		return this.userRepository.saveAndFlush(user);
	}


}
