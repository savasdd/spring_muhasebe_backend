package muhasebe.repository;

import muhasebe.model.MuhHesap;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class HesapRespositoryTest {

	@Autowired
	private MuhHesapRepository repository;

	// @Test
	void hesapEkleTest() {
		MuhHesap hesap = new MuhHesap();
		hesap.setHesapNo("125487");
		hesap.setAktif(true);

		MuhHesap newHesap = repository.save(hesap);
		Optional<MuhHesap> list = Optional.ofNullable(repository.findByHesapId(newHesap.getHesapId()));
		Assertions.assertThat(list).isPresent().hasValueSatisfying(p -> Assertions.assertThat(p).isEqualTo(hesap));
	}

	// @Test
	void hesapAllTest() {
		Collection<MuhHesap> list = repository.findAll();
		assertThat(list).hasSize(1);
		assertThat(list).isNotEmpty();
	}

	// @Test
	void hesapUpdateTest() {
		MuhHesap hesap = repository.findByHesapId("21799210-d593-4b69-9714-650d6124da78");
		assertThat(hesap).isNotNull();

		hesap.setHesapNo("753159");
		MuhHesap newHesap = repository.save(hesap);
		assertThat(hesap).isEqualTo(newHesap);

	}

	// @Test
	void hesapDeleteTest() {
		MuhHesap hesap = repository.findByHesapId("21799210-d593-4b69-9714-650d6124da78");
		repository.delete(hesap);
		assertThat(repository.count()).isEqualTo(0);
	}

	// @Test
	public void test() {
		List<Integer> actual = Arrays.asList(1, 2, 3, 4, 5);
		assertThat(actual).hasSize(5);
		assertThat(actual).contains(3, 5);
		assertThat(actual).endsWith(5);
	}

}
