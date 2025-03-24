package br.com.zup.cataliza.dtos;

import br.com.zup.cataliza.models.Roles;

import java.util.List;

public record UserRegister(
		String username,
		String password,
		String role
) {
}
