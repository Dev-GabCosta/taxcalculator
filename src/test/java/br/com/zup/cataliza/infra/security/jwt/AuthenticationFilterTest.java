package br.com.zup.cataliza.infra.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

class AuthenticationFilterTest {
	@Mock
	private TokenProvider tokenProvider;
	@Mock
	private UserDetailsService userDetailsService;
	@Mock
	private FilterChain filterChain;

	@InjectMocks
	private AuthenticationFilter authenticationFilter;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
	}

	@Test
	void testDoFilterInternal() throws ServletException, IOException {
		String token = "validToken";
		String username = "carlos324ab";
		request.addHeader("Authorization", "Bearer " + token);
		Mockito.when(tokenProvider.validateToken(token))
				.thenReturn(true);
		Mockito.when(tokenProvider.getUsername(token))
				.thenReturn(username);

		UserDetails userDetailsMock = Mockito.mock(UserDetails.class);
		Mockito.when(userDetailsMock.getUsername())
				.thenReturn(username);
		Mockito.when(userDetailsMock.getAuthorities())
				.thenReturn(null);
		Mockito.when(userDetailsService.loadUserByUsername(username))
				.thenReturn(userDetailsMock);
		authenticationFilter.doFilterInternal(request, response, filterChain);
		UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		Assertions.assertNotNull(authenticationToken);
		Assertions.assertEquals(username, authenticationToken.getName());
		Assertions.assertNull(authenticationToken.getCredentials());

		Mockito.verify(filterChain, Mockito.times(1))
				.doFilter(request, response);
	}


}