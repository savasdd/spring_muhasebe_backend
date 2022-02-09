package muhasebe.security;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
@SecurityScheme(name = "muhasebe", scheme = "Bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER, bearerFormat = "jwt")
public class SwaggerConfig {

	/*
	 * OpenAPI configurasyonu yapıldı. securty olarah schema name verildi ve bu name
	 * tüm controller tarafına da eklendi. grouped içinde hangi endpointleri
	 * tarayacağı belirtild.
	 **/

	@Bean
	public OpenAPI customOpenAPI(@Value("${application-description}") String appDesciption,
			@Value("${application-version}") String appVersion) {
		return new OpenAPI().info(new Info().title("sample application API").version(appVersion)
				.description(appDesciption).termsOfService("http://swagger.io/terms/")
				.license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}

	@Bean
	public GroupedOpenApi userOpenApi() {
		String packagesToscan[] = { "muhasebe.auth", "muhasebe.controller" };
		return GroupedOpenApi.builder().group("muhasebe").packagesToScan(packagesToscan).build();
	}

}
