package muhasebe.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import muhasebe.model.MuhKod;

public interface MuhKodRepository extends JpaRepository<MuhKod, String>, JpaSpecificationExecutor<MuhKod> {

	public MuhKod findByKodId(String kodId);

	@Query("select v from MuhKod v")
	public Page<MuhKod> getPageble(Pageable page);

	@Query("select v from MuhKod v where v.ustKod = :ustKod")
	public List<MuhKod> getKodByUstKod(String ustKod);

}
