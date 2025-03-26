package br.com.zup.cataliza.infra.security.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;
import java.io.PrintWriter;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentCaptor.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static  org.mockito.Mockito.when;

class JwtAuthenticationEntryPointTest {

	@Test
	void testCommence() throws IOException, ServletException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		AuthenticationException authenticationException = mock(AuthenticationException.class);
		JwtAuthenticationEntryPoint authenticationEntryPoint = new JwtAuthenticationEntryPoint();

		PrintWriter writer = mock(PrintWriter.class);
		when(response.getWriter()).thenReturn(writer);

		when(authenticationException.getMessage()).thenReturn("Test Error");

		authenticationEntryPoint.commence(request, response, authenticationException);

		ArgumentCaptor<String> jsonCaptor = forClass(String.class);
		verify(writer).write(jsonCaptor.capture());

		String expectedJson = "{\"error\": \"Falha na autenticação: Test Error\"}";
		assertEquals(expectedJson, jsonCaptor.getValue());

		verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		verify(response).setContentType("application/json");
		verify(response).setCharacterEncoding("UTF-8");
	}

}