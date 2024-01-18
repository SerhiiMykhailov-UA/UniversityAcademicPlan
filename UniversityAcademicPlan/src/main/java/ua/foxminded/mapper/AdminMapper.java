package ua.foxminded.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ua.foxminded.dto.AdminDto;
import ua.foxminded.entity.Admin;

@Mapper(componentModel = "spring")
public interface AdminMapper {
	
	@Mapping(target = "name", source = "admin.nickName")
	AdminDto adminToAdminDto (Admin admin, @Context CycleAvoidingMappingContext context);
	
	@Mapping(target = "nickName", source = "adminDto.name")
	Admin adminDtoToAdmin (AdminDto adminDto, @Context CycleAvoidingMappingContext context);

}
