package br.com.zup.cataliza.services;

import br.com.zup.cataliza.dtos.UserRegister;
import br.com.zup.cataliza.dtos.UserResponse;

public interface UserService {
	UserResponse createUser(UserRegister userRegister);
}
