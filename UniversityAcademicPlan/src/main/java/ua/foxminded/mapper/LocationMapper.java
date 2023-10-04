package ua.foxminded.mapper;

import org.mapstruct.Mapper;

import ua.foxminded.dto.LocationDto;
import ua.foxminded.entity.Location;

@Mapper(componentModel = "spring")
public interface LocationMapper {

	LocationDto locationToLocationDto (Location location);
	
	Location locationDtoToLocation (LocationDto locationDto);
	
}
