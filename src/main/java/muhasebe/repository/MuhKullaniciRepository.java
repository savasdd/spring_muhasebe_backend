package muhasebe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import muhasebe.model.MuhKullanici;

public interface MuhKullaniciRepository
		extends JpaRepository<MuhKullanici, Long>, JpaSpecificationExecutor<MuhKullanici> {

	public MuhKullanici findByKullaniciId(Long kullaniciId);

	public Optional<MuhKullanici> findByKullaniciAdi(String kullaniciAdi);

}
