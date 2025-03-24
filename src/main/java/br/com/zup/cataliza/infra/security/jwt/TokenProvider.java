package br.com.zup.cataliza.infra.security.jwt;


import br.com.zup.cataliza.models.CustomUserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Collection;
import java.util.Date;

@Component
public class TokenProvider {
	@Value("${SECRETKEY}")
	private String secretKey;
	private final long expirationDate = 3600000;

	public String generateToken(Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		String username = userDetails.getUsername();
		String role = userDetails.getAuthorities()
				              .iterator()
				              .next()
				              .getAuthority();
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + expirationDate);
		String token = Jwts.builder()
				               .subject(username)
				               .issuedAt(new Date())
				               .expiration(expireDate)
				               .claim("role", role)
				               .signWith(SignatureAlgorithm.HS256, key())
				               .compact();
		return token;
	}

	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
	}

	public String getUsername(String token) {
		return Jwts.parser()
				       .verifyWith((SecretKey) key())
				       .build()
				       .parseSignedClaims(token)
				       .getPayload()
				       .getSubject();
	}

	public boolean validateToken(String token) {
		Jwts.parser()
				.verifyWith((SecretKey) key())
				.build()
				.parse(token);
		return true;
	}
}
