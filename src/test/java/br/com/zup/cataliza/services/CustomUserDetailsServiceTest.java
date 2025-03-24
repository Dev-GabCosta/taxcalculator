package br.com.zup.cataliza.services;

import br.com.zup.cataliza.models.Role;
import br.com.zup.cataliza.models.User;
import br.com.zup.cataliza.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CustomUserDetailsServiceTest {
	private UserRepository userRepository = Mockito.mock(UserRepository.class);

	@InjectMocks
	private CustomUserDetailsService customUserDetailsService;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testLoadUserByUsername() {
		String username = "mariofernandes3852";
		String password = "mariopassword";
		String roleName = "ROLE_USER";
		Role role = new Role(roleName);
		User mockUser = new User(username, password, role);

		Mockito.when(userRepository.findByUsername(username))
				.thenReturn(Optional.of(mockUser));

		UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

		assertNotNull(userDetails);
		assertEquals(username, userDetails.getUsername());
		assertEquals(password, userDetails.getPassword());

		Mockito.verify(userRepository, Mockito.times(1))
				.findByUsername(username);
	}

}