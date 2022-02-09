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

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MUH_KOD")
public class MuhKod extends Auditable<String> {

	private static MuhKod instance = null;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "KOD_ID", updatable = false, nullable = false)
	private String kodId;

	@Column(name = "UST_KOD")
	private String ustKod;

	@NotNullValid(message = "Tanım alanı boş geçilemez")
	@Column(name = "TANIM")
	private String tanim;

	@NotNullValid(message = "Kod alanı boş geçilemez")
	@Column(name = "KOD")
	private String kod;

	@Column(name = "SIRA_NO")
	private Long siraNo;

	@Column(name = "ACIKLAMA")
	private String aciklama;

	@Version
	@Column(name = "VERSION")
	private Long version;

	public static MuhKod getInstance() {
		if (instance == null)
			instance = new MuhKod();

		return instance;
	}

}
