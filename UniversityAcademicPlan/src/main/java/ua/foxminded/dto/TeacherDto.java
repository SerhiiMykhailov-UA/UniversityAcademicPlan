package ua.foxminded.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.foxminded.entity.Course;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class TeacherDto {

	@NonNull
	private String firstName;
	@NonNull
	private String lastName;
	private List<Course> courses;
}
