package br.com.zup.cataliza.controllers;

import br.com.zup.cataliza.dtos.UserRegister;
import br.com.zup.cataliza.dtos.UserResponse;
import br.com.zup.cataliza.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
	@Mock
	private UserService userService;
	@InjectMocks
	private UserController userController;

	@Test
	void testCreateUser() throws Exception {
		String username = "gabs3425";
		String password = "senha23451";
		String role = "ROLE_USER";
		UserRegister userRegister = new UserRegister(username, password, role);
		UserResponse userResponse = new UserResponse(1L, username, role);
		when(userService.createUser(any(UserRegister.class))).thenReturn(userResponse);

		userController.createUser(userRegister);

		verify(userService, times(1)).createUser(any(UserRegister.class));
	}

}
