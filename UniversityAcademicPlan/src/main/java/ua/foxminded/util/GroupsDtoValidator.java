package ua.foxminded.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ua.foxminded.dto.GroupsDto;
import ua.foxminded.service.GroupsService;

@Component
public class GroupsDtoValidator implements Validator {

	private final GroupsService service;
	
	public GroupsDtoValidator(GroupsService service) {
		this.service = service;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return GroupsDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		GroupsDto dto = (GroupsDto) target;
		if (!service.ifExistsByName(dto.getName()))
			return;
		errors.rejectValue("name", "", "Group is allready exist");
		
	}


}
