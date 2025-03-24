package br.com.zup.cataliza.dtos;

import br.com.zup.cataliza.models.Roles;

public record UserResponse(
		Long id,
		String username,
		String role
) {
}
