package br.com.zup.cataliza.controllers;

import br.com.zup.cataliza.dtos.UserRegister;
import br.com.zup.cataliza.dtos.UserResponse;
import br.com.zup.cataliza.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/register")
public class UserController {
	private final UserService service;

	public UserController(UserService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<UserResponse> createUser(@RequestBody UserRegister userRegister) {
		UserResponse user = service.createUser(userRegister);
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}

}
