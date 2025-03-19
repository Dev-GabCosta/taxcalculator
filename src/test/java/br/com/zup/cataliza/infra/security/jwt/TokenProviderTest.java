package br.com.zup.cataliza.infra.security.jwt;

import br.com.zup.cataliza.infra.security.jwt.TokenProvider;

import java.lang.reflect.Field;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Mockito.*;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;

class TokenProviderTest {
	private TokenProvider tokenProvider;
	private Authentication authentication;
	private final String secretKey = "c2VjcmV0LWtleS10by1iZS11c2VkLWZvci10ZXN0aW5n";

	@BeforeEach
	void setup() throws NoSuchFieldException, IllegalAccessException {
		tokenProvider = new TokenProvider();
		authentication = Mockito.mock(Authentication.class);
		Field fieldSecretKey = TokenProvider.class.getDeclaredField("secretKey");
		fieldSecretKey.setAccessible(true);
		fieldSecretKey.set(tokenProvider, secretKey);
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