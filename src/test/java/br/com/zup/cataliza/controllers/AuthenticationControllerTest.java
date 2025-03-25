package br.com.zup.cataliza.controllers;

import br.com.zup.cataliza.dtos.Login;
import br.com.zup.cataliza.services.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {
	@Mock
	private AuthenticationService authenticationService;
	@InjectMocks
	private AuthenticationController authenticationController;

	@Test
	void testLogin() throws Exception {
		String username = "carlasoares2025";
		String password = "carla2349sre";
		Login loginRequest = new Login(username, password);

		String mockToken = "mocked-jwt-token";
		Mockito.when(authenticationService.login(Mockito.any(Login.class)))
				.thenReturn(mockToken);

		authenticationController.login(loginRequest);
		Mockito.verify(authenticationService, Mockito.times(1))
				.login(Mockito.any(Login.class));
	}

}