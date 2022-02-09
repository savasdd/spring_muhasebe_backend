package muhasebe.dto;

import java.util.Date;

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
public class MuhKiraciDto {

	@JsonView(MuhView.Public.class)
	private String kiraciId;

	@JsonView(MuhView.Public.class)
	private String ad;

	@JsonView(MuhView.Public.class)
	private String soyad;

	@JsonView(MuhView.Public.class)
	private String tckn;

	@JsonView(MuhView.Public.class)
	private String sicilNo;

	@JsonView(MuhView.Public.class)
	private String kurum;

	@JsonView(MuhView.Public.class)
	private String dogumYeri;

	@JsonView(MuhView.Public.class)
	private Date dogumTarihi;

	@JsonView(MuhView.Public.class)
	private MuhKodDto cinsiyet;

}
