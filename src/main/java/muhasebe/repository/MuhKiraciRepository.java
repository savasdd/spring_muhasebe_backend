package muhasebe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import muhasebe.model.MuhKiraci;

public interface MuhKiraciRepository extends JpaRepository<MuhKiraci, String>, JpaSpecificationExecutor<MuhKiraci> {

	public MuhKiraci findByKiraciId(String kiraciId);

}
