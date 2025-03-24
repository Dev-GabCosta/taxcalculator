package br.com.zup.cataliza.controllers;

import br.com.zup.cataliza.dtos.Login;
import br.com.zup.cataliza.dtos.LoginResponse;
import br.com.zup.cataliza.services.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class AuthenticationController {
	private final AuthenticationService service;

	public AuthenticationController(AuthenticationService service) {
		this.service = service;
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody Login login) {
		String token = service.login(login);
		LoginResponse response = new LoginResponse();
		response.setAccessToken(token);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
