package ua.foxminded.controller;

import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.foxminded.service.UsersService;

@Controller
@RequestMapping("/users")
public class UsersController {

	private UsersService usersService;

	public UsersController(UsersService usersService) {
		this.usersService = usersService;
	}
	
	@GetMapping()
	public String getAll(Model model) {
		model.addAttribute("usersDtoList", usersService.getAll());
		return "users/get_all";
	}
}
