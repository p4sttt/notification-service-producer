package net.d4y2k.producer.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.d4y2k.producer.api.dtos.CreateUserRequest;
import net.d4y2k.producer.api.dtos.UserDto;
import net.d4y2k.producer.api.exception.ValidationException;
import net.d4y2k.producer.api.mappers.UserMapper;
import net.d4y2k.producer.services.NotificationService;
import net.d4y2k.producer.services.UserService;
import net.d4y2k.producer.store.entities.UserEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	public static final String GET_USER_BY_ID = "/{user_id}";
	public static final String DELETE_USER_BY_ID = "/{user_id}";
	public static final String DO_SOMETHING = "/{user_id}/something";
	public static final String SUBSCRIBE_TO_USER = "/{user_id}/subscriptions/{friend_id}";

	private final UserService userService;
	private final UserMapper userMapper;
	private final NotificationService notificationService;

	@GetMapping
	ResponseEntity<List<UserDto>> fetchAll(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "15") int size
	) {
		Pageable pageable = PageRequest.of(page, size);
		List<UserEntity> users = userService.getAll(pageable);
		List<UserDto> userDtos = userMapper.userEntitiesToUserDtos(users);

		return ResponseEntity.ok(userDtos);
	}

	@GetMapping(GET_USER_BY_ID)
	ResponseEntity<UserDto> fetchById(@PathVariable("user_id") UUID userId) {
		UserEntity user = userService.getById(userId);
		UserDto userDto = userMapper.userEntityToUserDto(user);

		return ResponseEntity.ok(userDto);
	}

	@PostMapping
	ResponseEntity<UserDto> createNew(
			@RequestBody @Valid CreateUserRequest createUserRequest,
			BindingResult bindingResult
	) {
		if (bindingResult.hasErrors()) {
			throw new ValidationException(bindingResult);
		}

		UserEntity user = UserEntity.builder()
				.username(createUserRequest.username())
				.email(createUserRequest.email())
				.build();
		UserEntity createdUser = userService.makeDefault(user);
		UserDto userDto = userMapper.userEntityToUserDto(createdUser);

		return ResponseEntity.ok(userDto);
	}

	@DeleteMapping(DELETE_USER_BY_ID)
	ResponseEntity<String> deleteById(@PathVariable("user_id") UUID userId) {
		userService.deleteById(userId);

		return ResponseEntity.ok("User successfully deleted.");
	}

	@PostMapping(SUBSCRIBE_TO_USER)
	ResponseEntity<UserDto> addFriendToUser(
			@PathVariable("user_id") UUID userId,
			@PathVariable("friend_id") UUID friendId
	) {
		UserEntity user = userService.getById(userId);
		UserEntity friend = userService.getById(friendId);

		user.getSubscriptions().add(friend);
		UserEntity updatedUser = userService.save(user);

		UserDto userDto = userMapper.userEntityToUserDto(updatedUser);

		return ResponseEntity.ok(userDto);
	}

	@PostMapping(DO_SOMETHING)
	ResponseEntity<String> doSomething(@PathVariable("user_id") UUID userId) {
		UserEntity user = userService.getById(userId);
		List<UserEntity> subscribers = userService.getSubscribers(user);

		log.debug("Subscribers: {}", subscribers);

		for (UserEntity userEntity : subscribers) {
			notificationService.notifyForSomethingDone(userEntity);
		}

		return ResponseEntity.ok("You successfully done something.");
	}

}
