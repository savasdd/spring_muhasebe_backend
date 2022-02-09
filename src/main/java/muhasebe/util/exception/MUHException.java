package muhasebe.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MUHException extends Exception {

	/* Exception sınıfını extends eden custom hata sınıfı üretildi */
	private static final long serialVersionUID = -4969321983366153553L;

	public MUHException(String msg) {
		super(msg);
	}

}
