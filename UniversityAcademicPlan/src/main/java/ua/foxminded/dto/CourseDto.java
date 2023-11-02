package ua.foxminded.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

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
//	@ToString.Exclude
	private List<StudentDto> student;
	@ToString.Exclude
	private List<TeacherDto> teacher;
	private LocationDto location;
	@ToString.Exclude
	private List<ScheduleDto> schedule;
	@ToString.Exclude
	private List<LectureDto> lecture;
	@ToString.Exclude
	private List<GroupsDto> groups;
}
