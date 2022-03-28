package muhasebe.model;

import java.math.BigDecimal;

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
@Table(name = "MUH_ORAN")
public class MuhOran extends Auditable<String> {

	private static MuhOran instance = null;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "ORAN_ID", updatable = false, nullable = false)
	private String oranId;

	@NotNullValid(message = "Tanım alanı boş geçilemez")
	@Column(name = "TANIM")
	private String tanim;

	@NotNullValid(message = "Oran alanı boş geçilemez")
	@Column(name = "ORAN")
	private BigDecimal oran;

	@NotNullValid(message = "Yıl alanı boş geçilemez")
	@Column(name = "YIL")
	private Integer yil;

	@Column(name = "AKTIF")
	private Boolean aktif;

	@Version
	@Column(name = "VERSION")
	private Long version;

	public static MuhOran getInstance() {
		if (instance == null)
			instance = new MuhOran();
		return instance;
	}

}
