package br.com.zup.cataliza.services;

import br.com.zup.cataliza.dtos.Login;
import br.com.zup.cataliza.infra.security.jwt.TokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	private final AuthenticationManager authenticationManager;
	private final TokenProvider tokenProvider;

	public AuthenticationServiceImpl(AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
		this.authenticationManager = authenticationManager;
		this.tokenProvider = tokenProvider;
	}

	@Override
	public String login(Login login) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						login.username(),
						login.password()
				)
		);
		SecurityContextHolder.getContext()
				.setAuthentication(authentication);
		String token = tokenProvider.generateToken(authentication);
		return token;
	}
}
