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
@Table(name = "MUH_ROL_GRUP_ROL")
public class MuhRolGrupRol extends Auditable<String> {

	private static MuhRolGrupRol instance = null;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "ROL_GRUP_ROL_ID", updatable = false, nullable = false)
	private String rolGrupRolId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROL_GRUP_ID", nullable = false)
	private MuhRolGrup grup;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROL_NO", nullable = false)
	private MuhRol rol;

	@Version
	@Column(name = "VERSION")
	private Long version;

	@Column(name = "SORGU")
	private Boolean sorgu;

	@Column(name = "EKLEME")
	private Boolean ekleme;

	@Column(name = "GUNLEME")
	private Boolean gunleme;

	@Column(name = "SILME")
	private Boolean silme;

	public static MuhRolGrupRol getInstance() {
		if (instance == null)
			instance = new MuhRolGrupRol();
		return instance;
	}

}
