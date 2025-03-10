package br.com.zup.cataliza.dtos;

public record UserResponse(
		Long id,
		String username,
		String password
) {
}
