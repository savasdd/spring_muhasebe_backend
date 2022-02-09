package muhasebe.dto.auth;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import muhasebe.util.MuhView;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

	@JsonView(MuhView.Public.class)
	private String username;

	@JsonView(MuhView.Public.class)
	private String password;

}
