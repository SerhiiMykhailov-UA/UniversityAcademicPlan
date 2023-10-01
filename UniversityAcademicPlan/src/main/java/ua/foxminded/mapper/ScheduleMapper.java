package ua.foxminded.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import ua.foxminded.dto.ScheduleDto;
import ua.foxminded.entity.Schedule;

@Mapper
public interface ScheduleMapper {
	
	ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);
	
	ScheduleDto scheduleToScheduleDto (Schedule schedule);

}
