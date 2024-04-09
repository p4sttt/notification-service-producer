package net.d4y2k.producer.api.mappers.impl;

import net.d4y2k.producer.api.dtos.PreviewUserDto;
import net.d4y2k.producer.api.dtos.UserDto;
import net.d4y2k.producer.api.mappers.UserMapper;
import net.d4y2k.producer.store.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class UserMapperImpl implements UserMapper {
	@Override
	public PreviewUserDto userEntityToUserPreviewDto(UserEntity userEntity) {
		return PreviewUserDto.builder()
				.id(userEntity.getId())
				.username(userEntity.getUsername())
				.email(userEntity.getEmail())
				.createdAt(userEntity.getCreatedAt())
				.build();
	}

	@Override
	public UserDto userEntityToUserDto(UserEntity userEntity) {

		List<PreviewUserDto> subscriptions = new ArrayList<>();

		for (UserEntity subscription : userEntity.getSubscriptions()) {
			subscriptions.add(userEntityToUserPreviewDto(subscription));
		}

		return UserDto.builder()
				.id(userEntity.getId())
				.username(userEntity.getUsername())
				.subscriptions(subscriptions)
				.email(userEntity.getEmail())
				.createdAt(userEntity.getCreatedAt())
				.build();
	}

	@Override
	public List<UserDto> userEntitiesToUserDtos(Collection<UserEntity> userEntities) {
		return userEntities.stream()
				.map(this::userEntityToUserDto)
				.toList();
	}

}
