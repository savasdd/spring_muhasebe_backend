package muhasebe.util.aud;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditableImpl implements AuditorAware<String> {

	/*
	 * Aktif kullanıcıyı yakalamak adına Auditor interface implement ediliyor
	 */

	@Override
	public Optional<String> getCurrentAuditor() {
		return Optional.ofNullable(SecurityContextHolder.getContext()).map(e -> e.getAuthentication())
				.map(Authentication::getName);
	}

}
