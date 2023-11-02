package ua.foxminded.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ua.foxminded.entity.UserType;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TeacherDto extends UsersDto{

	private UserType userType;
	@NonNull
	private String firstName;
	@NonNull
	private String lastName;
	private List<CourseDto> courses;
}
