package muhasebe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import muhasebe.model.MuhOran;

public interface MuhOranRepository extends JpaRepository<MuhOran, String>, JpaSpecificationExecutor<MuhOran> {

	public MuhOran findByOranId(String oranId);

}
