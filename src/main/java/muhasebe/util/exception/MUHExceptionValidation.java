package muhasebe.util.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public class MUHExceptionValidation extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private List<String> add = new ArrayList<>();

	private final BindingResult errors;

	public MUHExceptionValidation(BindingResult errors) {
		this.errors = errors;
		setHandeler(errors);
	}

	@Override
	public String getMessage() {
		return this.getMessages().toString();
		// return add.toString();
	}

	// Custom
	public void setHandeler(BindingResult errors) {
		List<ObjectError> list = errors.getAllErrors();
		add.clear();

		for (ObjectError error : list) {
			if (error instanceof FieldError) {
				FieldError fieldError = (FieldError) error;
				Object invalidValue = fieldError.getRejectedValue();
				String mesaj = "";
				mesaj = mesaj + ":" + fieldError.getDefaultMessage() + " ";
				mesaj = mesaj.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
				add.add(String.format(mesaj, invalidValue));
			}
		}
	}

	public List<String> getMessages() {
		return getValidationMessage(this.errors);
	}

	// demonstrate how to extract a message from the binging result
	private List<String> getValidationMessage(BindingResult bindingResult) {
		return bindingResult.getAllErrors().stream().map(MUHExceptionValidation::getValidationMessage)
				.collect(Collectors.toList());
	}

	private static String getValidationMessage(ObjectError error) {
		if (error instanceof FieldError) {
			FieldError fieldError = (FieldError) error;
			String className = fieldError.getObjectName();
			String property = fieldError.getField();
			Object invalidValue = fieldError.getRejectedValue();
			String message = fieldError.getDefaultMessage();

			return String.format(message, invalidValue);
		}
		return String.format(error.getObjectName(), error.getDefaultMessage());
	}

}
