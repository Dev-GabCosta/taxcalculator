package br.com.zup.cataliza.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class CustomUserDetails implements UserDetails {
	private String username;
	private String password;
	private Set<GrantedAuthority> authorities;

	public CustomUserDetails(String username, String password, Set<GrantedAuthority> authorities) {
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	public String getSingleAuthority() {
		if (this.authorities != null && !this.authorities.isEmpty()) {
			return this.authorities.iterator().next().getAuthority();
		}
		return null; // Ou lançar uma exceção, dependendo do seu caso
	}


@Override
public String getPassword() {
	return this.password;
}

@Override
public String getUsername() {
	return this.username;
}

@Override
public boolean isAccountNonExpired() {
	return UserDetails.super.isAccountNonExpired();
}

@Override
public boolean isAccountNonLocked() {
	return UserDetails.super.isAccountNonLocked();
}

@Override
public boolean isCredentialsNonExpired() {
	return UserDetails.super.isCredentialsNonExpired();
}

@Override
public boolean isEnabled() {
	return UserDetails.super.isEnabled();
}

}
