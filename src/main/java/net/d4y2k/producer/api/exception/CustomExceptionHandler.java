package net.d4y2k.producer.api.exception;

import lombok.extern.slf4j.Slf4j;
import net.d4y2k.producer.api.dtos.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorDto> exception(NotFoundException exception) {
		ErrorDto errorDto = ErrorDto.builder()
				.status(HttpStatus.NOT_FOUND)
				.errorDescription(exception.getMessage())
				.build();

		return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorDto> exception(BadRequestException exception) {
		ErrorDto errorDto = ErrorDto.builder()
				.status(HttpStatus.BAD_REQUEST)
				.errorDescription(exception.getMessage())
				.build();

		return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ErrorDto> exception(ValidationException exception) {
		ErrorDto errorDto = ErrorDto.builder()
				.status(HttpStatus.BAD_REQUEST)
				.errorDescription(exception.getMessage())
				.error(exception.fieldsWithErrors)
				.build();

		return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
	}
}
