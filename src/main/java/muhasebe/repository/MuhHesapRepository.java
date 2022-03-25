package muhasebe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import muhasebe.model.MuhHesap;

public interface MuhHesapRepository extends JpaRepository<MuhHesap, String>, JpaSpecificationExecutor<MuhHesap> {

	public MuhHesap findByHesapId(String hesapId);

}
