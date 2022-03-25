package muhasebe.custom.async;

import java.util.Optional;

import muhasebe.model.MuhRefreshToken;

public interface MuhTokenRefreshServiceAsync {

	public Optional<MuhRefreshToken> findByToken(String token);

	public MuhRefreshToken createRefreshToken(String username);

	public MuhRefreshToken verifyExpiration(MuhRefreshToken token);

}
