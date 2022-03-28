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
public class MuhRolGrupDto {

	@JsonView(MuhView.Public.class)
	private String rolGrupId;

	@JsonView(MuhView.Public.class)
	private String grupAd;

	@JsonView(MuhView.Public.class)
	private String aciklama;

	@JsonView(MuhView.Public.class)
	private Boolean aktif;

}
