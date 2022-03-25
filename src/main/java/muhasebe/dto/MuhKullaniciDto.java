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
public class MuhKullaniciDto {

	@JsonView(MuhView.Public.class)
	private Long kullaniciId;

	@JsonView(MuhView.Public.class)
	private String ad;

	@JsonView(MuhView.Public.class)
	private String soyad;

	@JsonView(MuhView.Public.class)
	private String tckn;

	@JsonView(MuhView.Public.class)
	private String kullaniciAdi;

}
