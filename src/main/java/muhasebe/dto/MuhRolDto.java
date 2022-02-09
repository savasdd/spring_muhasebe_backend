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
public class MuhRolDto {

	@JsonView(MuhView.Public.class)
	private String rolId;

	@JsonView(MuhView.Public.class)
	private String tanim;

	@JsonView(MuhView.Public.class)
	private String kod;

	@JsonView(MuhView.Public.class)
	private String aciklama;

	@JsonView(MuhView.Public.class)
	private Boolean aktif;

}
