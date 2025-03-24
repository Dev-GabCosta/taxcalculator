package br.com.zup.cataliza.services;

import br.com.zup.cataliza.dtos.Login;
import br.com.zup.cataliza.infra.security.jwt.TokenProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {
	@Mock
	private AuthenticationManager authenticationManager;
	@Mock
	private TokenProvider tokenProvider;
	@InjectMocks
	AuthenticationServiceImpl authenticationService;

	@BeforeEach
	void setup() {
		SecurityContextHolder.clearContext();
	}

	@Test
	void testLogin() {
		String username = "anabia2341";
		String password = "anaBde14d8*";
		String expectedToken = "ana'sToken";
		Login login = new Login(username, password);
Authentication mockAuthentication = Mockito.mock(Authentication.class);
Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
		.thenReturn(mockAuthentication);
Mockito.when(tokenProvider.generateToken(mockAuthentication))
		.thenReturn(expectedToken);

String generatedToken = authenticationService.login(login);

		Assertions.assertEquals(expectedToken, generatedToken);
		Mockito.verify(authenticationManager, Mockito.times(1))
				.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
		Mockito.verify(tokenProvider, Mockito.times(1))
				.generateToken(mockAuthentication);
		Assertions.assertEquals(mockAuthentication, SecurityContextHolder.getContext().getAuthentication());
	}

}