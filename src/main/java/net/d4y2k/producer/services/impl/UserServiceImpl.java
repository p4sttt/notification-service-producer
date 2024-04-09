package net.d4y2k.producer.services.impl;

import lombok.RequiredArgsConstructor;
import net.d4y2k.producer.api.exception.NotFoundException;
import net.d4y2k.producer.services.UserService;
import net.d4y2k.producer.store.entities.UserEntity;
import net.d4y2k.producer.store.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	public List<UserEntity> getAll(Pageable pageable) {
		return userRepository.findAll(pageable).getContent();
	}

	@Override
	public UserEntity getById(UUID userId) {
		return userRepository.findById(userId).orElseThrow(
				() -> new NotFoundException(String.format("User with id: %s not found.", userId))
		);
	}

	@Override
	public Boolean existById(UUID userId) {
		return userRepository.existsById(userId);
	}

	@Override
	public UserEntity makeDefault(UserEntity user) {
		return userRepository.save(user);
	}

	@Override
	public UserEntity save(UserEntity user) {
		return userRepository.save(user);
	}

	@Override
	public void deleteById(UUID userId) {
		userRepository.deleteById(userId);
	}

	@Override
	public void delete(UserEntity user) {
		userRepository.delete(user);
	}

	@Override
	public List<UserEntity> getSubscribers(UserEntity user) {
		return userRepository.findAllBySubscriptionsContains(user);
	}

}