package ua.foxminded.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ua.foxminded.dto.CourseDto;
import ua.foxminded.service.CourseService;

@Component
public class CourseDtoValidator implements Validator {
	
	private final CourseService service;

	public CourseDtoValidator(CourseService service) {
		this.service = service;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return CourseDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CourseDto dto = (CourseDto) target;
		if (!service.ifExistsByName(dto.getName()))
			return;
		errors.rejectValue("name", "", "Course is allready exist");
	}

}
