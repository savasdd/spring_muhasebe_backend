package muhasebe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Muhasebe Projesi Swagger Dokümantasyonu", version = "1.0"))
//@EnableElasticsearchRepositories(basePackages = "muhasebe.repository")
public class MuhasebeProjesiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MuhasebeProjesiApplication.class, args);
	}

}
