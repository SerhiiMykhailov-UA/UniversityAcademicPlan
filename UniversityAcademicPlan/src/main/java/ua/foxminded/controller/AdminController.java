package ua.foxminded.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import ua.foxminded.dto.UsersDto;
import ua.foxminded.entity.UserType;
import ua.foxminded.exceptions.UsersException;
import ua.foxminded.service.UsersService;
import ua.foxminded.util.UsersDtoValidator;

@Controller
@RequestMapping("/adminpanel")
public class AdminController {

	private final UsersDtoValidator usersDtoValidator;
	private final UsersService service;
	
	public AdminController(UsersDtoValidator usersDtoValidator, UsersService service) {
		this.usersDtoValidator = usersDtoValidator;
		this.service = service;
	}
	
	@GetMapping("/registration")
	public String registrationPage(@ModelAttribute("users") UsersDto users) {
		return "adminpanel/registration";
	}
	
	@PostMapping("/registration")
	public String performRegistration(@ModelAttribute("users") @Valid UsersDto users, BindingResult bindingResult) {
		usersDtoValidator.validate(users, bindingResult);
		
		if (bindingResult.hasErrors())
			return "adminpanel/registration";
		
		users.setUserType(UserType.ROLE_NEWUSER);
		service.add(users);
		return "redirect:/showUserPage";
	}
	
	@GetMapping("/{id}")
	public String updateUsers(@PathVariable("id") long id, Model model) {
		UsersDto usersDto;
		try {
			usersDto = service.get(id);
			model.addAttribute("usersDto", usersDto);
		} catch (UsersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "adminpanel/users";
	}
	
	@PostMapping("/{id}")
	public String updateUsers(@PathVariable("id") long id, @ModelAttribute("usersDto") UsersDto usersDto) {
		try {
			service.update(usersDto);
		} catch (UsersException e) {
			e.getMessage();
		}
		return "redirect:/showUserPage";
	}
	
	@PostMapping("/delet")
	public String deletUser(@ModelAttribute("usersDto") UsersDto usersDto) {
		System.out.println(usersDto);
		service.delete(usersDto.getId());
		return "redirect:/showUserPage";
	}
}
