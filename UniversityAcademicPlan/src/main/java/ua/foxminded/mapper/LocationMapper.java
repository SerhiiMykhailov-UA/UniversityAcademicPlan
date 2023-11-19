package ua.foxminded.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;

import ua.foxminded.dto.LocationDto;
import ua.foxminded.entity.Location;

@Mapper(componentModel = "spring")
public interface LocationMapper {

	LocationDto locationToLocationDto (Location location, @Context CycleAvoidingMappingContext context);
	
	Location locationDtoToLocation (LocationDto locationDto, @Context CycleAvoidingMappingContext context);
	
}
