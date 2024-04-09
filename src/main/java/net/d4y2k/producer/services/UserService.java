package net.d4y2k.producer.services;

import net.d4y2k.producer.store.entities.UserEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface UserService {

	List<UserEntity> getAll(Pageable pageable);

	UserEntity getById(UUID userId);

	Boolean existById(UUID userId);

	UserEntity makeDefault(UserEntity user);

	UserEntity save(UserEntity user);

	void deleteById(UUID userId);

	void delete(UserEntity user);

	List<UserEntity> getSubscribers(UserEntity user);

}