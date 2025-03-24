package br.com.zup.cataliza.services;

import br.com.zup.cataliza.dtos.UserRegister;
import br.com.zup.cataliza.dtos.UserResponse;
import br.com.zup.cataliza.models.Role;
import br.com.zup.cataliza.models.Roles;
import br.com.zup.cataliza.models.User;
import br.com.zup.cataliza.repositories.RoleRepository;
import br.com.zup.cataliza.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Optional;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	@Mock
	private UserRepository userRepository;
	@Mock
	private RoleRepository roleRepository;
	@Mock
	BCryptPasswordEncoder passwordEncoder;
	private UserServiceImpl service;
	private UserRegister userRegister;
	private Role role;
	private User user;
	private String username = "fernandesmario@email.com";
	private String password = "flij8139.";
	private String roleName = "ROLE_ADMIN";

	@BeforeEach
	void setUp() {
		userRegister = new UserRegister(username, password, roleName);
		role = new Role(roleName);
		user = new User(username, password, role);

		service = new UserServiceImpl(userRepository, roleRepository, passwordEncoder);
	}

	@Test
	void testCreateUserWithRole() {
		when(roleRepository.findByName(roleName)).thenReturn(Optional.of(role));
		when(roleRepository.findByName(userRegister.role())).thenReturn(Optional.of(role));
		when(passwordEncoder.encode(userRegister.password())).thenReturn("encodedPassword");
		when(userRepository.save(any(User.class))).thenReturn(user);

		UserResponse userResponse = service.createUser(userRegister);

		assertNotNull(userResponse);
		assertEquals(username, userResponse.username());
		assertEquals(roleName, userResponse.role());
		verify(roleRepository, times(1)).findByName(userRegister.role());
		verify(roleRepository, never()).save(any(Role.class));
		verify(userRepository, times(1)).save(any(User.class));
	}

	@Test
	void testCreateUserWithNewRole() {
		when(roleRepository.findByName(roleName)).thenReturn(Optional.of(role));
		when(roleRepository.findByName(userRegister.role())).thenReturn(Optional.empty());
		when(roleRepository.save(any(Role.class))).thenReturn(role);
		when(passwordEncoder.encode(userRegister.password())).thenReturn("encodedPassword");
		when(userRepository.save(any(User.class))).thenReturn(user);

		UserResponse userResponse = service.createUser(userRegister);

		assertNotNull(userResponse);
		assertEquals(username, userResponse.username());
		assertEquals("ROLE_ADMIN", userResponse.role());
		verify(roleRepository, times(1)).findByName(userRegister.role());
		verify(roleRepository, times(1)).save(any(Role.class));
		verify(userRepository, times(1)).save(any(User.class));
	}

	@Test
	void testCreateUserWithInvalidRole() {
		userRegister = new UserRegister(username, "encodedPassword", "INVALID_ROLE");

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			service.createUser(userRegister);
		});

		assertEquals("Role inválida: INVALID_ROLE", exception.getMessage());
		verify(roleRepository, never()).findByName(anyString());
		verify(roleRepository, never()).save(any(Role.class));
		verify(userRepository, never()).save(any(User.class));
	}
/*
	@Test
	void testCreateUser() throws NoSuchFieldException, IllegalAccessException {
		String username = "gabs3452";
		String password = "senha3452";
		String role = "ROLE_USER";
		UserRegister userRegister = new UserRegister(username, password, role);
		Role savedRole = new Role(role);
		when(roleRepository.save(any(Role.class)))
				.thenReturn(savedRole);
		when(userRepository.save(any(User.class))).thenAnswer(
				invocation -> {
					User user = invocation.getArgument(0);
					Field idField = User.class.getDeclaredField("id");
					idField.setAccessible(true);
					idField.set(user, 1L);
					return user;
				}
		);
		UserResponse createdUser = service.createUser(userRegister);
		assertNotNull(createdUser);
		assertEquals(1L, createdUser.id());
		assertEquals(username, createdUser.username());
		assertEquals(role, createdUser.role());
		verify(roleRepository, times(1))
				.save(any(Role.class));
		verify(userRepository, times(1)).save(any(User.class));
	}
*/

}