package ua.foxminded.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;

import ua.foxminded.dto.ScheduleDto;
import ua.foxminded.entity.Schedule;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
	
	ScheduleDto scheduleToScheduleDto (Schedule schedule, @Context CycleAvoidingMappingContext context);
	
	Schedule scheduleDtoToSchedule (ScheduleDto scheduleDto, @Context CycleAvoidingMappingContext context);
	
}
