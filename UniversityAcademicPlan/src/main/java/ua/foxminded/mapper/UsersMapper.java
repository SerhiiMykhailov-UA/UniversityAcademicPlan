package ua.foxminded.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ua.foxminded.dto.UsersDto;
import ua.foxminded.entity.Users;

@Mapper(componentModel = "spring")
public interface UsersMapper {
	
	@Mapping(target = "name", source = "users.nickName")
	UsersDto usersToUsersDto (Users users, @Context CycleAvoidingMappingContext context);
	
	@Mapping(target = "nickName", source = "usersDto.name")
	Users usersDtoToUsers (UsersDto usersDto, @Context CycleAvoidingMappingContext context);

}
