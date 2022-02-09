package muhasebe.dto;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import muhasebe.util.MuhView;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MuhKodDto {

	/*
	 * Farklı kullanıcıların alan üzerinde kontrolunu sağlamak adına @JsonView
	 * kullanıldı ve MuhView sınıfının Public sınıfı eklendi.
	 */

	@JsonView(MuhView.Public.class)
	private String kodId;

	@JsonView(MuhView.Public.class)
	private String ustKod;

	@JsonView(MuhView.Public.class)
	private String tanim;

	@JsonView(MuhView.Public.class)
	private String kod;

	@JsonView(MuhView.Public.class)
	private Long siraNo;

	@JsonView(MuhView.Public.class)
	private String aciklama;

}
