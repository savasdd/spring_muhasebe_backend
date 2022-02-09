package muhasebe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import muhasebe.model.MuhRol;

public interface MuhRolRepository extends JpaRepository<MuhRol, String>, JpaSpecificationExecutor<MuhRol> {

	public MuhRol findByRolId(String rolId);

	@Query("select v from MuhRol v")
	public Page<MuhRol> getPageble(Pageable page);

}
