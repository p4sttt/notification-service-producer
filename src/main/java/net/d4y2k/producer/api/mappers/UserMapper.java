package net.d4y2k.producer.api.mappers;

import net.d4y2k.producer.api.dtos.PreviewUserDto;
import net.d4y2k.producer.api.dtos.UserDto;
import net.d4y2k.producer.store.entities.UserEntity;

import java.util.Collection;
import java.util.List;

public interface UserMapper {

	PreviewUserDto userEntityToUserPreviewDto(UserEntity userEntity);
	UserDto userEntityToUserDto(UserEntity userEntity);
	List<UserDto> userEntitiesToUserDtos(Collection<UserEntity> userEntities);

}
