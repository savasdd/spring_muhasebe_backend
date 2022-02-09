package muhasebe.model;

import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

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
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Document(indexName = "kullanicilog")
public class MuhKullaniciLog {

	@Id
	@JsonView(MuhView.Public.class)
	private String id;

	@JsonView(MuhView.Public.class)
	@Field(name = "kullaniciAdi", type = FieldType.Text)
	private String kullaniciAdi;

	@JsonView(MuhView.Public.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	@Field(name = "createDate", type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date createDate;

	@JsonView(MuhView.Public.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	@Field(name = "expireDate", type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date expireDate;

}
