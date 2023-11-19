package ua.foxminded.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.foxminded.dto.UsersDto;
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
		List<UsersDto> usersDtoList = usersService.getAll().stream().sorted(Comparator.comparingLong(UsersDto::getId)).collect(Collectors.toList());
		model.addAttribute("usersDtoList", usersDtoList);
		return "users/get_all";
	}
}
