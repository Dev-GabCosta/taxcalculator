package br.com.zup.cataliza.services;

import br.com.zup.cataliza.dtos.UserRegister;
import br.com.zup.cataliza.dtos.UserResponse;
import br.com.zup.cataliza.models.User;
import br.com.zup.cataliza.repositories.UserRepository;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class UserServiceTest {

	@Test
	void testCreateUser() throws NoSuchFieldException, IllegalAccessException{
		String username = "gabs3452";
		String password = "senha3452";
		UserRepository userRepository = mock(UserRepository.class);
		UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository);
		UserRegister userRegister = new UserRegister(username, password, null);
		when(userRepository.save(any(User.class))).thenAnswer(
				invocation -> {
					User user = invocation.getArgument(0);
					Field idField = User.class.getDeclaredField("id");
					idField.setAccessible(true);
					idField.set(user, 1L);
					return user;
				}
		);
		UserResponse createdUser = userServiceImpl.createUser(userRegister);
		assertNotNull(createdUser);
		assertEquals(1L, createdUser.id());
		assertEquals(username, createdUser.username());
		assertEquals(password, createdUser.password());
		verify(userRepository, times(1)).save(any(User.class));
	}


}