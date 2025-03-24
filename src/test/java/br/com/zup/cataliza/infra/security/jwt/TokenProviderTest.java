package br.com.zup.cataliza.infra.security.jwt;

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
		String username = "Alana_Cristina";
		Authentication authentication = mock(Authentication.class);
		when(authentication.getName())
				.thenReturn(username);
		String token = tokenProvider.generateToken(authentication);

		assertNotNull(token);
		assertFalse(token.isEmpty());
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