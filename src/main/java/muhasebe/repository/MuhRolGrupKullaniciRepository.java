package muhasebe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import muhasebe.model.MuhRolGrupKullanici;

public interface MuhRolGrupKullaniciRepository
		extends JpaRepository<MuhRolGrupKullanici, String>, JpaSpecificationExecutor<MuhRolGrupKullanici> {

	public MuhRolGrupKullanici findByRolGrupKullaniciId(String rolGrupKullaniciId);

}
