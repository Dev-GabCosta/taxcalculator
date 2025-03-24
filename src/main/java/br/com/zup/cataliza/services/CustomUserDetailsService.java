package br.com.zup.cataliza.services;

import br.com.zup.cataliza.models.CustomUserDetails;
import br.com.zup.cataliza.models.User;
import br.com.zup.cataliza.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByUsername(username).orElseThrow(() ->
				                                                            new UsernameNotFoundException("Nome de usuário não encontrado"));

		Set<GrantedAuthority> authorities = Collections.singleton(
				new SimpleGrantedAuthority(
						user.getRole().getName()
				)
		);

		return new CustomUserDetails(
				username,
				user.getPassword(),
				authorities
		);
	}

}
