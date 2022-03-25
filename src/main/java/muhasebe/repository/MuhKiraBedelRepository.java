package muhasebe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import muhasebe.model.MuhKiraBedel;

public interface MuhKiraBedelRepository
		extends JpaRepository<MuhKiraBedel, String>, JpaSpecificationExecutor<MuhKiraBedel> {

	public MuhKiraBedel findByKiraId(String kiraId);

	// @Query("select v from MuhKiraBedel v where v.kiraci.kiraciId = :kiraci and
	// v.kiraId = :kiraId")
	public Optional<MuhKiraBedel> findByKiraci_KiraciIdAndKiraId(String kiraciId, String kiraId);

	// @Query("select v from MuhKiraBedel v where v.kiraci.kiraciId = :kiraci")
	public List<MuhKiraBedel> findByKiraci_KiraciId(String kiraciId);

	// @Query("select v from MuhKiraBedel v where v.kiraci.kiraciId = :kiraciId")
	public Page<MuhKiraBedel> findByKiraci_KiraciId(String kiraciId, Pageable page);

}
