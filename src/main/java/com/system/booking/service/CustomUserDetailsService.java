package com.system.booking.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.system.booking.entity.AppUser;
import com.system.booking.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private final UserRepository userRepository;
	
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByEmail(username)
        		.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        System.out.println(user.toString());
        return User
        	.withUsername(user.getEmail())
        	.password(user.getPassword())
        	.build();
	}
}
