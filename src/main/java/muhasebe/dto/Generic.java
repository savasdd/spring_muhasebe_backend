package muhasebe.dto;

import java.util.List;

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
public class Generic<T> {

	public static final Generic<?> EMPTY = new Generic<>();

	@JsonView(MuhView.Public.class)
	private List<T> data;

	@JsonView(MuhView.Public.class)
	private Integer totalPage;

	@JsonView(MuhView.Public.class)
	private Long totalCount;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("<");
		if (this.data != null) {
			builder.append(this.data);
			builder.append(',');
		}
		builder.append(this.data);
		builder.append('>');
		return builder.toString();
	}

}
