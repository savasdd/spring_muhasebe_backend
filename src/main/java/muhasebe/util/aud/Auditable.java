package muhasebe.util.aud;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable<U> {

	/*
	 * Audit durumu için kullanılıyor. Springin AuditingEntityListener sınıfı
	 * kullanılıyor. Springin CreatedBy, CreatedDate ve LastModify özellikleri burda
	 * işimizi görüyor. En son tablo üzerinde yapılan logları bu sayede takip
	 * edebiliyoruz.
	 */

	@CreatedBy
	@Column(name = "CREATED_BY")
	private U createdBy;

	@CreatedDate
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@LastModifiedBy
	@Column(name = "UPDATED_BY")
	private U updatedBy;

	@LastModifiedDate
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

}
