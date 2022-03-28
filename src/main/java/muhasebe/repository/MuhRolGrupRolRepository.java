package muhasebe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import muhasebe.model.MuhRolGrupRol;

public interface MuhRolGrupRolRepository
		extends JpaRepository<MuhRolGrupRol, String>, JpaSpecificationExecutor<MuhRolGrupRol> {

	public MuhRolGrupRol findByRolGrupRolId(String rolGrupRolId);

}
