package muhasebe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import muhasebe.model.MuhKiraci;

public interface MuhKiraciRepository extends JpaRepository<MuhKiraci, String>, JpaSpecificationExecutor<MuhKiraci> {

	public MuhKiraci findByKiraciId(String kiraciId);

	@Query("select v from MuhKiraci v")
	public Page<MuhKiraci> getPageble(Pageable page);

}
