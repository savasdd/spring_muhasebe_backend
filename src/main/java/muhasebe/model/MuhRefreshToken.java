package muhasebe.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "MUH_REFRESH_TOKEN")
public class MuhRefreshToken extends Auditable<String> {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "ID", updatable = false, nullable = false)
	private String id;

	@Column(name = "KULLANICI_ADI")
	private String kullaniciAdi;

	@Column(name = "TOKEN")
	private String token;

	@Column(name = "EXPIRY_DATE")
	private Instant expiryDate;

}
