package ua.foxminded.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ua.foxminded.dto.ScheduleDto;
import ua.foxminded.service.ScheduleService;

@Component
public class ScheduleDtoValidator implements Validator {

	private final ScheduleService service;
	
	public ScheduleDtoValidator(ScheduleService service) {
		this.service = service;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return ScheduleDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ScheduleDto dto = (ScheduleDto) target;
		if (!service.existsByStartTimeAndEndTimeAndDayOfWeek(dto.getStartTime(), dto.getEndTime(), dto.getDayOfWeek()))
			return;
		errors.rejectValue("name", "", "Schedule is allready exist");
		
	}

}
