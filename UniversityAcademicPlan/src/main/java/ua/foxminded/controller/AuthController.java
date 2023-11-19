package ua.foxminded.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import ua.foxminded.dto.UsersDto;
import ua.foxminded.entity.UserType;
import ua.foxminded.service.UsersService;
import ua.foxminded.util.UsersDtoValidator;

@Controller
@RequestMapping("/auth")
public class AuthController {

	private final UsersDtoValidator usersDtoValidator;
	private final UsersService service;
	
	public AuthController(UsersDtoValidator usersDtoValidator, UsersService service) {
		this.usersDtoValidator = usersDtoValidator;
		this.service = service;
	}

	@GetMapping("/login")
	public String logingPage() {
		return "auth/login";
	}
	
	@GetMapping("/registration")
	public String registrationPage(@ModelAttribute("users") UsersDto users) {
		return "auth/registration";
	}
	
	@PostMapping("/registration")
	public String performRegistration(@ModelAttribute("users") @Valid UsersDto users, BindingResult bindingResult) {
		usersDtoValidator.validate(users, bindingResult);
		
		if (bindingResult.hasErrors())
			return "/auth/registration";
		
		users.setUserType(UserType.ROLE_NEWUSER);
		service.add(users);
		return "redirect:/auth/login";
	}
}
