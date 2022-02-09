package muhasebe.dto;

import java.math.BigDecimal;

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
public class MuhOranDto {

	@JsonView(MuhView.Public.class)
	private String oranId;

	@JsonView(MuhView.Public.class)
	private String tanim;

	@JsonView(MuhView.Public.class)
	private BigDecimal oran;

	@JsonView(MuhView.Public.class)
	private Integer yil;

	@JsonView(MuhView.Public.class)
	private Boolean aktif;

}
