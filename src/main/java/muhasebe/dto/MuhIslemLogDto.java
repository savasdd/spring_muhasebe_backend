package muhasebe.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class MuhIslemLogDto {

	@JsonView(MuhView.Public.class)
	private String id;

	@JsonView(MuhView.Public.class)
	private String kullaniciAdi;

	@JsonView(MuhView.Public.class)
	private String servis;

	@JsonView(MuhView.Public.class)
	private String metot;

	@JsonView(MuhView.Public.class)
	private String path;

	@JsonView(MuhView.Public.class)
	private Long status;

	@JsonView(MuhView.Public.class)
	private String body;

	@JsonView(MuhView.Public.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	private Date createDate;

}
