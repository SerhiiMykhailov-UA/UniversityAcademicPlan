package ua.foxminded.dto;

import java.util.List;

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
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class CourseDto {

	private long id;
	@NonNull
	private String name;
	private List<StudentDto> student;
	private List<TeacherDto> teacher;
	private LocationDto location;
	private List<ScheduleDto> schedule;
	private List<LectureDto> lecture;
	private List<GroupsDto> groups;
}
