package br.com.zup.cataliza.infra.security;

import br.com.zup.cataliza.infra.security.jwt.AuthenticationFilter;
import br.com.zup.cataliza.infra.security.jwt.JwtAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	private AuthenticationFilter authenticationFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
		security.csrf(
						csrf -> csrf.disable()
				)
				.authorizeHttpRequests(
						(authorize) -> {
							authorize.requestMatchers("/user/login").permitAll();
							authorize.requestMatchers(HttpMethod.POST, "/user/register").permitAll();
							authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
							authorize.anyRequest().authenticated();
						}
				)
				.httpBasic(Customizer.withDefaults());
		security.exceptionHandling(
				exception -> exception.authenticationEntryPoint(authenticationEntryPoint)
		);
		security.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return security.build();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
