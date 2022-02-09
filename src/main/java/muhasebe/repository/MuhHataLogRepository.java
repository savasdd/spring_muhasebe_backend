package muhasebe.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import muhasebe.model.MuhHataLog;

public interface MuhHataLogRepository extends ElasticsearchRepository<MuhHataLog, String> {

	public MuhHataLog findByKullaniciAdi(String kullaniciAdi);
}
