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
@Table(name = "MUH_ROL_GRUP_KULLANICI")
public class MuhRolGrupKullanici extends Auditable<String> {

	private static MuhRolGrupKullanici instance = null;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "ROL_GRUP_ROL_ID", updatable = false, nullable = false)
	private String rolGrupKullaniciId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROL_GRUP_ID", nullable = false)
	private MuhRolGrup grup;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "KULLANICI_ID", nullable = false)
	private MuhKullanici kullanici;

	@Version
	@Column(name = "VERSION")
	private Long version;

	public static MuhRolGrupKullanici getInstance() {
		if (instance == null)
			instance = new MuhRolGrupKullanici();
		return instance;
	}

}
