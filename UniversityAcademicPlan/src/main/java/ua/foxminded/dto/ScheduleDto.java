package ua.foxminded.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class ScheduleDto {
	
	private long id;

	private String nameSchedule;
	
	@NonNull
	private LocalTime startTime;
	
	@NonNull
	private LocalTime endTime;
	
	@NonNull
	private DayOfWeek dayOfWeek;
	
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private List<CourseDto> course;
	

}
