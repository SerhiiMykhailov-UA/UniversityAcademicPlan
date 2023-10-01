package ua.foxminded.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import ua.foxminded.dto.LocationDto;
import ua.foxminded.entity.Location;

@Mapper
public interface LocationMapper {

	LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);
	
	LocationDto locationToLocationDto (Location location);
}
