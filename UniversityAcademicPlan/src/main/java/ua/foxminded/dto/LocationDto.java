package ua.foxminded.dto;

import java.util.List;

import javax.persistence.Entity;

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
public class LocationDto {
	
	private long id;
	@NonNull
	private String name;
	private List<CourseDto> course;

}
