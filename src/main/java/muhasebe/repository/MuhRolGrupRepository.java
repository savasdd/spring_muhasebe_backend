package muhasebe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import muhasebe.model.MuhRolGrup;

public interface MuhRolGrupRepository extends JpaRepository<MuhRolGrup, String>, JpaSpecificationExecutor<MuhRolGrup> {

	public MuhRolGrup findByRolGrupId(String rolGrupId);

}
