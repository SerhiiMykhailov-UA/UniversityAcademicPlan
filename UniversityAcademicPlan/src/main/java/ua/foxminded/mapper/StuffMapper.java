package ua.foxminded.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ua.foxminded.dto.StuffDto;
import ua.foxminded.entity.Stuff;

@Mapper(componentModel = "spring")
public interface StuffMapper {
	
	@Mapping(target = "name", source = "stuff.nickName")
	StuffDto stuffToStuffDto (Stuff stuff, @Context CycleAvoidingMappingContext context);
	
	@Mapping(target = "nickName", source = "stuffDto.name")
	Stuff stuffDtoToStuff (StuffDto stuffDto, @Context CycleAvoidingMappingContext context);
}
