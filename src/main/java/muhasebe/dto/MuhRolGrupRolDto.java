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
public class MuhRolGrupRolDto {

	@JsonView(MuhView.Public.class)
	private String rolGrupRolId;

	@JsonView(MuhView.Public.class)
	private MuhRolGrupDto grup;

	@JsonView(MuhView.Public.class)
	private MuhRolDto rol;

	@JsonView(MuhView.Public.class)
	private Boolean sorgu;

	@JsonView(MuhView.Public.class)
	private Boolean ekleme;

	@JsonView(MuhView.Public.class)
	private Boolean gunleme;

	@JsonView(MuhView.Public.class)
	private Boolean silme;

}
