package muhasebe.security;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

@Configuration
@PropertySource("classpath:application.properties")
class ElasticsearchConfig {

	@Value("${spring_data_elastic}")
	private String url;

	@Bean
	RestHighLevelClient elasticsearchClient() {
		final ClientConfiguration clientConfiguration = ClientConfiguration.builder().connectedTo(url).build();
		return RestClients.create(clientConfiguration).rest();
	}
}