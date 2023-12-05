package ua.foxminded.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;

import ua.foxminded.dto.AdminDto;
import ua.foxminded.entity.Admin;

@Mapper(componentModel = "spring")
public interface AdminMapper {
	
	AdminDto adminToAdminDto (Admin admin, @Context CycleAvoidingMappingContext context);
	
	Admin adminDtoToAdmin (AdminDto adminDto, @Context CycleAvoidingMappingContext context);

}
