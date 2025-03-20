package br.com.zup.cataliza.infra.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.TestPropertySource;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {"JWT_SECRET=c2VjcmV0LWtleS10by1iZS11c2VkLWZvci10ZXN0aW5n"})
class TokenProviderTest {
	@Autowired
	private TokenProvider tokenProvider;
	private Authentication authentication;
	private final String secretKey = "c2VjcmV0LWtleS10by1iZS11c2VkLWZvci10ZXN0aW5n";

	@BeforeEach
	void setup() throws NoSuchFieldException, IllegalAccessException {
		authentication = Mockito.mock(Authentication.class);
	}

	@Test
	void testGenerateToken() {
		String username = "Alana_Cristina";
		Mockito.when(authentication.getName())
				.thenReturn(username);
		String token = tokenProvider.generateToken(authentication);

		assertNotNull(token);
		assert (!token.isEmpty());
		Key key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
		Claims claims = Jwts.parser()
				                .setSigningKey(key)
				                .build()
				                .parseClaimsJws(token)
				                .getBody();

		assertEquals(username, claims.getSubject());
		assertNotNull(claims.getIssuedAt());
		assertNotNull(claims.getExpiration());
		Date now = new Date();
		Date expiration = claims.getExpiration();
		assertTrue(expiration.after(now));
	}

}