package br.com.zup.cataliza.controllers;

import br.com.zup.cataliza.dtos.UserRegister;
import br.com.zup.cataliza.dtos.UserResponse;
import br.com.zup.cataliza.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@MockitoBean
	private UserService userService;

	@Test
	void testCreateUser() throws Exception {
		String username = "gabs3425";
		String password = "senha23451";
		UserRegister userRegister = new UserRegister(username, password, null);
		UserResponse userResponse = new UserResponse(1L, username, password);
		when(userService.createUser(any(UserRegister.class))).thenReturn(userResponse);
		mockMvc.perform(post("/user/register")
				                .contentType("application/json")
				                .content(objectMapper.writeValueAsString(userRegister))
				)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.username")
						           .value(username))
				.andExpect(jsonPath("$.password")
						           .value(password));

		verify(userService, times(1)).createUser(any(UserRegister.class));
	}

}
