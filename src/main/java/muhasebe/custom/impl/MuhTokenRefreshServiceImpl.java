package muhasebe.custom.impl;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import muhasebe.custom.async.MuhTokenRefreshServiceAsync;
import muhasebe.model.MuhRefreshToken;
import muhasebe.repository.MuhRefreshTokenRepository;
import muhasebe.util.EnumUtil;
import muhasebe.util.exception.TokenRefreshException;

@Component
@RequiredArgsConstructor
public class MuhTokenRefreshServiceImpl implements MuhTokenRefreshServiceAsync {

	private final MuhRefreshTokenRepository refreshTokenRepository;

	@Override
	public Optional<MuhRefreshToken> findByToken(String token) {
		return refreshTokenRepository.findByToken(token);
	}

	@Override
	public MuhRefreshToken createRefreshToken(String username) {
		MuhRefreshToken refreshToken = new MuhRefreshToken();
		String sha3Hex = new DigestUtils("SHA-256")
				.digestAsHex(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));

		refreshToken.setKullaniciAdi(username);
		refreshToken.setExpiryDate(Instant.now().plusMillis(EnumUtil.TOKEN_REFRESH_EXPIRE_TIME));
		refreshToken.setToken(sha3Hex);
		refreshToken = refreshTokenRepository.save(refreshToken);
		return refreshToken;
	}

	@Override
	public MuhRefreshToken verifyExpiration(MuhRefreshToken token) {
		if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
			refreshTokenRepository.delete(token);
			throw new TokenRefreshException(token.getToken(),
					"Refresh token was expired. Please make a new signin request");
		}

		return token;
	}

}
