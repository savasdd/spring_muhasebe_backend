package muhasebe.util.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.ObjectUtils;

public class NotNullValidator implements ConstraintValidator<NotNullValid, Object> {

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {

		if (ObjectUtils.isNotEmpty(value) || value != null) {
			return true;
		} else
			return false;
	}

}