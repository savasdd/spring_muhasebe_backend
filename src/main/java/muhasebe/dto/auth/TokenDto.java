package muhasebe.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import muhasebe.util.MuhView;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {
	/*
	 * Sadece okunubilir olması adına READ_ONLY setlendi
	 */

	@JsonProperty(access = Access.READ_ONLY)
	@JsonView(MuhView.Public.class)
	private String user;

	@JsonProperty(access = Access.READ_ONLY)
	@JsonView(MuhView.Public.class)
	private String token;

}
