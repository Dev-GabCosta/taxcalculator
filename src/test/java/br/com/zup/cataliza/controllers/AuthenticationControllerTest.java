package br.com.zup.cataliza.controllers;

import br.com.zup.cataliza.dtos.Login;
import br.com.zup.cataliza.services.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthenticationControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@MockitoBean
	private AuthenticationService authenticationService;

	@Test
	void testLogin() throws Exception {
		String username = "carlasoares2025";
		String password = "carla2349sre";
		Login loginRequest = new Login(username, password);

		String mockToken = "mocked-jwt-token";
		Mockito.when(authenticationService.login(Mockito.any(Login.class)))
				.thenReturn(mockToken);
		mockMvc.perform(post("/user/login")
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(objectMapper.writeValueAsString(loginRequest)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.accessToken").value(mockToken));

		Mockito.verify(authenticationService, Mockito.times(1))
				.login(Mockito.any(Login.class));
	}

}