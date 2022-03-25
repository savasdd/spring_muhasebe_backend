package muhasebe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import muhasebe.model.MuhRol;

public interface MuhRolRepository extends JpaRepository<MuhRol, String>, JpaSpecificationExecutor<MuhRol> {

	public MuhRol findByRolId(String rolId);

}
