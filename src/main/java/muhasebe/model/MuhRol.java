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
@Table(name = "MUH_ROL")
public class MuhRol extends Auditable<String> {

	private static MuhRol instance = null;

	public static MuhRol getInstance() {
		if (instance == null)
			instance = new MuhRol();
		return instance;
	}

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "ROL_ID", updatable = false, nullable = false)
	private String rolId;

	@NotNullValid(message = "Tanım alanı boş geçilemez")
	@Column(name = "TANIM")
	private String tanim;

	@NotNullValid(message = "Kod alanı boş geçilemez")
	@Column(name = "KOD")
	private String kod;

	@Column(name = "ACIKLAMA")
	private String aciklama;

	@Column(name = "AKTIF")
	private Boolean aktif;

	@Version
	@Column(name = "VERSION")
	private Long version;

}
