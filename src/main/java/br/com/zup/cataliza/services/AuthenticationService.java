package br.com.zup.cataliza.services;

import br.com.zup.cataliza.dtos.Login;

public interface AuthenticationService {
	String login(Login login);
}
