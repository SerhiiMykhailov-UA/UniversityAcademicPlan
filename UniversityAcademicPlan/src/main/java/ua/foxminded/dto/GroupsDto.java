package ua.foxminded.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.foxminded.entity.Course;
import ua.foxminded.entity.Student;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class GroupsDto {

	private long id;
	@NonNull
	private String name;
	private List<Student> student;
	private List<Course> course;

}
