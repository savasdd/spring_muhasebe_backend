package muhasebe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import muhasebe.model.MuhRefreshToken;

public interface MuhRefreshTokenRepository extends JpaRepository<MuhRefreshToken, String> {

	Optional<MuhRefreshToken> findByToken(String token);

}
