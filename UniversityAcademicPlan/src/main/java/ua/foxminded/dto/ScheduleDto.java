package ua.foxminded.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ua.foxminded.entity.Users;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class ScheduleDto {
	
	private long id;
	@NonNull
	private LocalTime startTime;
	@NonNull
	private LocalTime endTime;
	@NonNull
	private DayOfWeek dayOfWeek;
	private CourseDto course;
	

}
