package br.com.zup.cataliza.infra.security.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class JwtAuthenticationEntryPointTest {

	@Test
	void testCommence() throws IOException,      ServletException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		AuthenticationException authenticationException = mock(AuthenticationException.class);
		JwtAuthenticationEntryPoint authenticationEntryPoint = new JwtAuthenticationEntryPoint();
		authenticationEntryPoint.commence(request, response, authenticationException);
		verify(response)
				.sendError(HttpServletResponse.SC_UNAUTHORIZED, null);
	}


}