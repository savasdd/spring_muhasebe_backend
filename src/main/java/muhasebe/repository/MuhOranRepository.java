package muhasebe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import muhasebe.model.MuhOran;

public interface MuhOranRepository extends JpaRepository<MuhOran, String>, JpaSpecificationExecutor<MuhOran> {

	public MuhOran findByOranId(String oranId);

	@Query("select v from MuhOran v")
	public Page<MuhOran> getPageble(Pageable page);

}
