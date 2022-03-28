package muhasebe.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Table(name = "MUH_KIRACI")
public class MuhKiraci extends Auditable<String> {

	private static MuhKiraci instance = null;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "KIRACI_ID", updatable = false, nullable = false)
	private String kiraciId;

	@NotNullValid(message = "Ad alanı boş geçilemez")
	@Column(name = "AD")
	private String ad;

	@NotNullValid(message = "Soyad alanı boş geçilemez")
	@Column(name = "SOYAD")
	private String soyad;

	@NotNullValid(message = "T.C. No alanı boş geçilemez")
	@NumberValid(message = "T.C. No alanı numerik olmak zorunda")
	@Column(name = "TCKN")
	private String tckn;

	@Column(name = "SICIL_NO")
	@NumberValid(message = "Sicil No alanı numerik olmak zorunda")
	private String sicilNo;

	@Column(name = "KURUM")
	private String kurum;

	@Column(name = "DOGUM_YERI")
	private String dogumYeri;

	@Temporal(TemporalType.DATE)
	@Column(name = "DOGUM_TARIHI")
	private Date dogumTarihi;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CINSIYET", referencedColumnName = "KOD_ID", nullable = true)
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private MuhKod cinsiyet;

	@Version
	@Column(name = "VERSION")
	private Long version;

	public static MuhKiraci getInstance() {
		if (instance == null)
			instance = new MuhKiraci();
		return instance;
	}

}
