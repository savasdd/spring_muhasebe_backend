package muhasebe.util.aud;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "aware")
public class AuditConfig {

	/*
	 * Yazılan audit impl için bean yazıldı.
	 */

	@Bean
	public AuditorAware<String> aware() {
		return new AuditableImpl();
	}

}
