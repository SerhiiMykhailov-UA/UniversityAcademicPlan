package ua.foxminded.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;

import ua.foxminded.dto.UsersDto;
import ua.foxminded.entity.Users;

@Mapper(componentModel = "spring")
public interface UsersMapper {
	
//	UsersMapper INSTANCE = Mappers.getMapper(UsersMapper.class);
	
	UsersDto usersToUsersDto (Users users, @Context CycleAvoidingMappingContext context);
	
	Users usersDtoToUsers (UsersDto usersDto, @Context CycleAvoidingMappingContext context);

}
