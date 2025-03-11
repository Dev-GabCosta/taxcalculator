package br.com.zup.cataliza.services;

import br.com.zup.cataliza.dtos.UserRegister;
import br.com.zup.cataliza.dtos.UserResponse;
import br.com.zup.cataliza.models.User;
import br.com.zup.cataliza.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserResponse createUser(UserRegister userRegister){
		User user = new User(userRegister.username(), userRegister.password());
		userRepository.save(user);
		return new UserResponse(
				user.getId(),
				user.getUsername(),
				user.getPassword()
		);
	}

}
