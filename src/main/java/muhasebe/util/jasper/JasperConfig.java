package muhasebe.util.jasper;

import java.io.IOException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@PropertySource("classpath:application.properties")
public class JasperConfig {

	@Value("${spring.datasource.url}")
	private String url;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	@Bean
	public DataSource dataSource() throws IOException {
		DriverManagerDataSource driver = new DriverManagerDataSource();
		driver.setDriverClassName("org.postgresql.Driver");
		driver.setUrl(url);
		driver.setUsername(username);
		driver.setPassword(password);
		driver.setSchema("muhsb");
		return driver;
	}

	@Bean
	public ReportFilter filter() {
		return new ReportFilter();
	}

	@Bean
	public ReportExporter exporter() {
		return new ReportExporter();
	}

}
