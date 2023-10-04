package ua.foxminded.mapper;

import org.mapstruct.Mapper;

import ua.foxminded.dto.ScheduleDto;
import ua.foxminded.entity.Schedule;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
	
	ScheduleDto scheduleToScheduleDto (Schedule schedule);
	
	Schedule scheduleDtoToSchedule (ScheduleDto scheduleDto);

}
