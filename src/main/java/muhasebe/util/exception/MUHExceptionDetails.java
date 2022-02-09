package muhasebe.util.exception;

import java.time.LocalDateTime;

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
public class MUHExceptionDetails {

	@JsonView(MuhView.Public.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
	private LocalDateTime date;

	@JsonView(MuhView.Public.class)
	private String message;

	@JsonView(MuhView.Public.class)
	private String details;

	@JsonView(MuhView.Public.class)
	private Integer status;

}
