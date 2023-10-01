package ua.foxminded.dto;

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
public class LectureDto {

	private long id;
	@NonNull
	private String name;
	private Course course;
}
