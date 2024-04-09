package net.d4y2k.producer.store.repository;

import net.d4y2k.producer.store.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
	Page<UserEntity> findAll(Pageable pageable);
	List<UserEntity> findAllBySubscriptionsContains(UserEntity subscriptions);
}
