package ua.foxminded.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import ua.foxminded.dto.UsersDto;
import ua.foxminded.entity.Users;

@Mapper
public interface UsersMapper {
	
	UsersMapper INSTANCE = Mappers.getMapper(UsersMapper.class);
	
	UsersDto usersToUsersDto (Users users);
	
	Users usersDtoToUsers (UsersDto usersDto);

}
