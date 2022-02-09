package muhasebe.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenManager {

	public static final long JWT_TOKEN_EXPIRE_TIME = 5_900_000;
	Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	public String generateToken(String username) {
		return Jwts.builder().setSubject(username).setIssuer("muhproject")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_EXPIRE_TIME)).signWith(key).compact();

	}

	public boolean tokenValidate(String tokan) {
		if (getUserFromToken(tokan) != null && isExpired(tokan)) {
			return true;
		}
		return false;
	}

	public String getUserFromToken(String token) {
		Claims claims = getClaims(token); // key ile token çözüldü ve içindeki değerler parse edildi Clams
		return claims.getSubject();// user
	}

	public Boolean isExpired(String token) {// geçerlilik
		Claims claims = getClaims(token);
		return claims.getExpiration().after(new Date(System.currentTimeMillis()));// bitiş zamanından sonra ise
	}

	private Claims getClaims(String token) {
		return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
	}

}
