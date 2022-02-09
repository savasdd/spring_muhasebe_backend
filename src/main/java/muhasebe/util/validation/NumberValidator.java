package muhasebe.util.validation;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.ObjectUtils;

public class NumberValidator implements ConstraintValidator<NumberValid, Object> {

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (value != null)
			return validate(value);
		else if (ObjectUtils.isNotEmpty(value))
			return validate(value);
		else
			return true;
	}

	public Boolean validate(Object value) {
		Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
		Boolean valid = value != null ? pattern.matcher(value.toString()).matches() : true;

		if (valid)
			return true;
		else
			return false;
	}

}