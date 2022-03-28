package muhasebe.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import muhasebe.util.aud.Auditable;
import muhasebe.util.validation.NotNullValid;
import muhasebe.util.validation.NumberValid;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MUH_KULLANICI")
public class MuhKullanici extends Auditable<String> {

	/*
	 * Güvenlik açısından daha korunaklı olması adına hibernate UUID yöntemini
	 * kullandım.Lombok get/set, toString ve Arg özelliklerini kullandım.
	 */

	private static MuhKullanici instance = null;

	@Id
	@Column(name = "KULLANICI_ID", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GNL_GEN")
	@SequenceGenerator(name = "GNL_GEN", sequenceName = "MUH_SEQ", allocationSize = 1)
	private Long kullaniciId;

	@NotNullValid(message = "Ad alanı boş geçilemez")
	@Column(name = "AD")
	private String ad;

	@NotNullValid(message = "Soyad alanı boş geçilemez")
	@Column(name = "SOYAD")
	private String soyad;

	@NumberValid(message = "T.C. No alanı numerik olmak zorunda")
	@Column(name = "TCKN")
	private String tckn;

	@NotNullValid(message = "Kullanıcı adı alanı boş geçilemez")
	@Column(name = "KULLANICI_ADI")
	private String kullaniciAdi;

	@NotNullValid(message = "Şifre alanı boş geçilemez")
	@Column(name = "SIFRE")
	private String sifre;

	@Version
	@Column(name = "VERSION")
	private Long version;

	public static MuhKullanici getInstance() {
		if (instance == null)
			instance = new MuhKullanici();
		return instance;
	}
}
