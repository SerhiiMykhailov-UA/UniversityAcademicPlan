package ua.foxminded.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ua.foxminded.dto.UsersDto;
import ua.foxminded.service.UsersService;

@Component
public class UsersDtoValidator implements Validator{
	
	private final UsersService service;
	

	public UsersDtoValidator(UsersService service) {
		this.service = service;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return UsersDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UsersDto dto = (UsersDto) target;
		
		if (!service.isExistsByNickName(dto.getName()))
			return;
		errors.rejectValue("nickName", "", "User is allready exists");
	}

}
