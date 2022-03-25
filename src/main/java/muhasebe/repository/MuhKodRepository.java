package muhasebe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import muhasebe.model.MuhKod;

public interface MuhKodRepository extends JpaRepository<MuhKod, String>, JpaSpecificationExecutor<MuhKod> {

	public MuhKod findByKodId(String kodId);

	public List<MuhKod> findKodByUstKod(String ustKod);

}
