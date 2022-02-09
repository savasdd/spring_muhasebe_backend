package muhasebe.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import muhasebe.model.MuhKullaniciLog;

public interface MuhKullaniciLogRepository extends ElasticsearchRepository<MuhKullaniciLog, String> {

	public MuhKullaniciLog findByKullaniciAdi(String kullaniciAdi);
}
