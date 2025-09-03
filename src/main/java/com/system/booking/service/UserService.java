package com.system.booking.service;

import java.util.Optional;

import com.system.booking.entity.AppUser;

public interface UserService {

	Optional<AppUser> findUserById(Long id);
	AppUser createUser(AppUser user);
	AppUser updateUser(AppUser user);
}
