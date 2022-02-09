package muhasebe.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

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
	 * kullandım.Lombokun get/set, toString ve Arg özelliklerini kullandım.
	 */

	private static MuhKullanici instance = null;

	public static MuhKullanici getInstance() {
		if (instance == null)
			instance = new MuhKullanici();
		return instance;
	}

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "KULLANICI_ID", updatable = false, nullable = false)
	private String kullaniciId;

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

}
