package net.d4y2k.producer.api.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {

	public final Map<String, Object> fieldsWithErrors = new HashMap<>();

	public ValidationException(BindingResult bindingResult) {
		super("Validation exception.");

		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		for (FieldError fieldError : fieldErrors) {
			Map<String, Object> reason = new HashMap<>();

			List<String> messages = bindingResult.getFieldErrors(fieldError.getField())
					.stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
					.toList();
			reason.put("reasons", messages);
			reason.put("rejected_value", fieldError.getRejectedValue());

			fieldsWithErrors.put(fieldError.getField(), reason);
		}
	}
}
