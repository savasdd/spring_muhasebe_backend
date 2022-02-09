package muhasebe.repository;

import muhasebe.model.MuhKiraBedel;
import muhasebe.model.MuhKod;
import muhasebe.util.exception.MUHException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class KodRespositoryTest {

	@Autowired
	private MuhKodRepository kod;

	@Autowired
	private MuhKiraBedelRepository bedel;

	// @Test
	void kodCreateTest() {
		MuhKod dto = new MuhKod();
		dto.setTanim("test2");
		dto.setKod("test2");
		dto.setSiraNo(22L);

		MuhKod newKod = kod.save(dto);

		Optional<MuhKod> list = kod.findById(newKod.getKodId());
		assertThat(list).isPresent().hasValueSatisfying(p -> assertThat(p).isEqualTo(dto));
		assertThat(list).isPresent().hasValueSatisfying(p -> assertThat(p.getTanim()).isEqualTo("merhaba"));
		assertThat(list).isPresent().hasValueSatisfying(p -> assertThat(p.getSiraNo()).isEqualTo(22L));
	}

	// @Test
	void bedelCreateTest() {
		MuhKiraBedel dto = new MuhKiraBedel();
		dto.setTanim("emlak");
		dto.setBedel(new BigDecimal(256));
		dto.setToplamBedel(new BigDecimal(256));
		dto.setKiraci(null);// zorunlu

		assertThatThrownBy(() -> bedel.save(dto)).hasMessageContaining("Kiraci kaydı bunulamadı")
				.isInstanceOf(MUHException.class);
	}

	// @Test
	void kodDeleteTest() {
		MuhKod dto = new MuhKod();
		dto.setKod("deneme");
		dto.setTanim("deneme");
		MuhKod newKod = kod.save(dto);

		kod.deleteById(newKod.getKodId());
		assertThat(kod.count()).isEqualTo(1);
	}

	// @Test
	void kodDeleteAllTest() {
		MuhKod dto = new MuhKod();
		dto.setKod("deneme");
		dto.setTanim("deneme");
		kod.save(dto);

		kod.deleteAll();
		assertThat(kod.count()).isEqualTo(0);
	}
}
