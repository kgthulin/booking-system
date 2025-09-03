package com.system.booking.controller;

import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.system.booking.dto.UserDto;
import com.system.booking.entity.AppUser;
import com.system.booking.exception.EmailVerificationFailException;
import com.system.booking.exception.PasswordNotMatchException;
import com.system.booking.exception.ResourceNotFoundException;
import com.system.booking.service.EmailVerificationService;
import com.system.booking.service.UserService;
import com.system.booking.util.JwtUtil;

@RequestMapping("api/users/")
@RestController
public class UserController {

	private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final EmailVerificationService emailVerificationService;
    
	public UserController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService,
			PasswordEncoder passwordEncoder, EmailVerificationService emailVerificationService) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		this.emailVerificationService = emailVerificationService;
	}
    
	@PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AppUser loginUser) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        		loginUser.getEmail(), 
        		loginUser.getPassword()));
        String token = jwtUtil.generateToken(auth.getName());
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(token);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AppUser newUser) {
    	String email = newUser.getEmail();
    	if (Strings.isEmpty(email)) {
    		throw new RuntimeException("Email cannot be empty!");
    	}
    	
    	if (!this.emailVerificationService.sendVerifyEmail(email)) {
    		throw new EmailVerificationFailException(email + " cannot be verified!");
    	}
    	
        newUser.setPassword(this.passwordEncoder.encode(newUser.getPassword()));
        this.userService.createUser(newUser);
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body("Email verified and registered successfully!");
    }
    
    @GetMapping("/{userId}")
    public ResponseEntity<AppUser> getProfile(@PathVariable("userId") Long id) {
    	AppUser user = this.userService.findUserById(id).orElseThrow(() -> new ResourceNotFoundException());
    	return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(user);
    }
    
    @PutMapping("/{userId}/changepassword")
    public ResponseEntity<String> changePassword(@PathVariable("userId") Long id, @RequestBody UserDto changePasswordUser) {
    	AppUser user = this.userService.findUserById(id).orElseThrow(() -> new ResourceNotFoundException());
    	if (this.passwordEncoder.matches(changePasswordUser.getOldPassword(), user.getPassword())) {
    		String newPassword = this.passwordEncoder.encode(changePasswordUser.getNewPassword());
    		user.setPassword(newPassword);
    	} else {
    		throw new PasswordNotMatchException();
    	}
    	this.userService.updateUser(user);
    	return ResponseEntity.status(HttpStatusCode.valueOf(200)).body("Changed password successfully!");
    }
    
    @PutMapping("/{userId}/resetpassword")
    public ResponseEntity<String> resetPassword(@PathVariable("userId") Long id, @RequestBody AppUser resetPasswordUser) {
    	AppUser user = this.userService.findUserById(id).orElseThrow(() -> new ResourceNotFoundException());
    	if (!this.emailVerificationService.sendVerifyEmail(user.getEmail())) {
    		throw new EmailVerificationFailException(user.getEmail() + " cannot be verified!");
    	}
    	String newPassword = this.passwordEncoder.encode(resetPasswordUser.getPassword());
    	user.setPassword(newPassword);
    	this.userService.updateUser(user);
    	return ResponseEntity.status(HttpStatusCode.valueOf(200)).body("Reset password successfully!");
    }
}
