package muhasebe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import muhasebe.model.MuhHesap;

public interface MuhHesapRepository extends JpaRepository<MuhHesap, String>, JpaSpecificationExecutor<MuhHesap> {

	public MuhHesap findByHesapId(String hesapId);

	@Query("select p from MuhHesap p")
	public Page<MuhHesap> getPageble(Pageable page);

}
