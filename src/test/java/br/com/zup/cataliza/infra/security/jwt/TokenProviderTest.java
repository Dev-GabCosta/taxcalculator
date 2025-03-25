package br.com.zup.cataliza.infra.security.jwt;

import br.com.zup.cataliza.models.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


class TokenProviderTest {
	private TokenProvider tokenProvider;
	private String secretKey = "c2VjcmV0LWtleS10by1iZS11c2VkLWZvci10ZXN0aW5n";

	@BeforeEach
	void setup() throws NoSuchFieldException, IllegalAccessException {
		tokenProvider = new TokenProvider();
		ReflectionTestUtils.setField(tokenProvider, "secretKey",
				secretKey);
	}

	@Test
	void testGenerateToken() {
		String username = "maria2384";
		String role = "ROLE_USER";
		Authentication authentication = mock(Authentication.class);
		CustomUserDetails userDetails = mock(CustomUserDetails.class);
		when(authentication.getPrincipal()).thenReturn(userDetails);
		when(userDetails.getUsername()).thenReturn(username);
		when(userDetails.getSingleAuthority()).thenReturn(role);
		String token = tokenProvider.generateToken(authentication);

		assertNotNull(token);
		Key key = Keys.hmacShaKeyFor(java.util.Base64.getUrlDecoder().decode(secretKey));

		Claims claims = (Claims) Jwts.parser()
				                         .setSigningKey(key)
				                         .build()
				                         .parseClaimsJws(token)
				                         .getBody();

		assertEquals(username, claims.getSubject());
		assertEquals(role, claims.get("role"));
		assertNotNull(claims.getIssuedAt());
		assertNotNull(claims.getExpiration());
	}

}