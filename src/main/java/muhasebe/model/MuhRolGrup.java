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

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MUH_ROL_GRUP")
public class MuhRolGrup extends Auditable<String> {

	private static MuhRolGrup instance = null;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "ROL_GRUP_ID", updatable = false, nullable = false)
	private String rolGrupId;

	@Column(name = "GRUP_AD")
	private String grupAd;

	@Column(name = "ACIKLAMA")
	private String aciklama;

	@Column(name = "AKTIF")
	private Boolean aktif;

	@Version
	@Column(name = "VERSION")
	private Long version;

	public static MuhRolGrup getInstance() {
		if (instance == null)
			instance = new MuhRolGrup();
		return instance;
	}

}
