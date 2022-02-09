package muhasebe.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import muhasebe.model.MuhIslemLog;

public interface MuhIslemLogRepository extends ElasticsearchRepository<MuhIslemLog, String> {

	public MuhIslemLog findByKullaniciAdi(String kullaniciAdi);
}
