package muhasebe.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MUH_HESAP")
public class MuhHesap extends Auditable<String> {

	private static MuhHesap instance = null;

	public static MuhHesap getInstance() {
		if (instance == null)
			instance = new MuhHesap();
		return instance;
	}

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "HESAP_ID", updatable = false, nullable = false)
	private String hesapId;

	@NotNullValid(message = "Tanım alanı boş geçilemez")
	@Column(name = "TANIM")
	private String tanim;

	@NotNullValid(message = "Hesap no alanı boş geçilemez")
	@Column(name = "HESAP_NO")
	private String hesapNo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "KOD_ID", referencedColumnName = "KOD_ID", nullable = true)
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private MuhKod kod;

	@Column(name = "AKTIF")
	private Boolean aktif;

	@Version
	@Column(name = "VERSION")
	private Long version;

}
