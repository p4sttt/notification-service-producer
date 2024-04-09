package net.d4y2k.producer.api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequest(

		@NotNull(message = "Username cannot be null.")
		@NotBlank(message = "Username cannot be blank.")
		String username,
		@NotNull(message = "Email cannot be null.")
		@NotBlank(message = "Email cannot be blank.")
		@Email(message = "Email is not valid.")
		String email

) { }