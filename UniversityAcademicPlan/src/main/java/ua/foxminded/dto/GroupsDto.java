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
public class GroupsDto {

	private long id;
	@NonNull
	private String name;
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private List<StudentDto> student;
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private List<CourseDto> course;

}
