package ua.foxminded.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;

import ua.foxminded.dto.StuffDto;
import ua.foxminded.entity.Stuff;

@Mapper(componentModel = "spring")
public interface StuffMapper {
	
	StuffDto stuffToStuffDto (Stuff stuff, @Context CycleAvoidingMappingContext context);
	
	Stuff stuffDtoToStuff (StuffDto stuffDto, @Context CycleAvoidingMappingContext context);
}
