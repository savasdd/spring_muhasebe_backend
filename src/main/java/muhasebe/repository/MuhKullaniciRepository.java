package muhasebe.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import muhasebe.model.MuhKullanici;

public interface MuhKullaniciRepository
		extends JpaRepository<MuhKullanici, String>, JpaSpecificationExecutor<MuhKullanici> {

	public MuhKullanici findByKullaniciId(String kullaniciId);

	@Query("select v from MuhKullanici v where v.kullaniciAdi = :kullaniciAdi")
	public Optional<MuhKullanici> getByKullaniciAdi(String kullaniciAdi);

	@Query("select v from MuhKullanici v")
	public Page<MuhKullanici> getPageble(Pageable page);

}
