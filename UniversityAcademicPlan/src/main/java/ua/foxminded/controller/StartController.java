package ua.foxminded.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ua.foxminded.dto.UsersDto;
import ua.foxminded.exceptions.UsersException;
import ua.foxminded.security.UsersDetails;
import ua.foxminded.service.StudentService;
import ua.foxminded.service.TeacherService;
import ua.foxminded.service.UsersService;

@Controller
public class StartController {

	private final UsersService usersService;
		
	public StartController(UsersService usersService) {
		this.usersService = usersService;
	}

	@GetMapping()
	public String start() {
		System.out.println(11111);
		return "index";
	}
	
	@GetMapping("/showUserInfo")
	public String showUserInfo(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UsersDetails details = (UsersDetails) authentication.getPrincipal();
		model.addAttribute("usersDetails", details);
		UsersDto usersDto;
		try {
			usersDto = usersService.getByNickName(details.getUsername());
			model.addAttribute("usersDto", usersDto);
		} catch (UsersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "showuserInfo";
	}
	
	@GetMapping("/admin")
	public String adminPage() {
		return "admin";
	}
}
