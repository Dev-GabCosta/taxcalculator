package br.com.zup.cataliza.services;

import br.com.zup.cataliza.dtos.UserRegister;
import br.com.zup.cataliza.dtos.UserResponse;
import br.com.zup.cataliza.models.Role;
import br.com.zup.cataliza.models.Roles;
import br.com.zup.cataliza.models.User;
import br.com.zup.cataliza.repositories.RoleRepository;
import br.com.zup.cataliza.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public UserResponse createUser(UserRegister userRegister) {
		Roles enumRole;

		enumRole = convertRole(userRegister.role());

		String roleName = enumRole.name();
		Optional<Role> optionalRole = roleRepository.findByName(userRegister.role());

		Role role = optionalRole.orElseGet(() -> {
			Role newRole = new Role(roleName);
			return roleRepository.save(newRole);
		});

		String encodedPassword = passwordEncoder.encode(userRegister.password());
		User user = new User(userRegister.username(), encodedPassword, role);
		userRepository.save(user);
		return new UserResponse(
				user.getId(),
				user.getUsername(),
				user.getRole().getName()
		);
	}

	private static Roles convertRole(String role) {
		if ("ROLE_ADMIN".equalsIgnoreCase(role)) {
			return Roles.ROLE_ADMIN;
		} else if ("ROLE_USER".equalsIgnoreCase(role)) {
			return Roles.ROLE_USER;
		} else {
			throw new IllegalArgumentException("Role inválida: " + role);
		}
	}

}
