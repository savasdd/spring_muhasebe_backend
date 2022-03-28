package muhasebe.model;

import java.math.BigDecimal;
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
@Table(name = "MUH_KIRA_BEDEL")
public class MuhKiraBedel extends Auditable<String> {

	private static MuhKiraBedel instance = null;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "KIRA_ID", updatable = false, nullable = false)
	private String kiraId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "KIRACI", referencedColumnName = "KIRACI_ID", nullable = false)
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private MuhKiraci kiraci;

	@Column(name = "TANIM")
	private String tanim;

	@NotNullValid(message = "Kira süresi alanı boş geçilemez")
	@NumberValid(message = "Kira süresi alanı numeric olmak zorunda")
	@Column(name = "KIRA_SURESI")
	private Integer kiraSuresi;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FAIZ_ORANI", referencedColumnName = "ORAN_ID", nullable = true)
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private MuhOran faizOrani;

	@Temporal(TemporalType.DATE)
	@Column(name = "KIRALAMA_TARIHI")
	private Date kiralamaTarihi;

	@NotNullValid(message = "Bedel alanı boş geçilemez")
	@Column(name = "BEDEL")
	private BigDecimal bedel;

	@Column(name = "TOPLAM_BEDEL")
	private BigDecimal toplamBedel;

	@Version
	@Column(name = "VERSION")
	private Long version;

	public static MuhKiraBedel getInstance() {
		if (instance == null)
			instance = new MuhKiraBedel();
		return instance;
	}

}
