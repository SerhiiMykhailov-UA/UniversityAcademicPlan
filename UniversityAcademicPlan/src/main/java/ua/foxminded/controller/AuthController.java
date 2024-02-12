package ua.foxminded.controller;

import java.net.MalformedURLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.foxminded.dto.UsersDto;
import ua.foxminded.service.CourseService;

@Controller
//@RequestMapping("/auth")
public class AuthController {
	
	private final CourseService courseService;

	
	public AuthController(CourseService courseService) {
		this.courseService = courseService;
	}

	@GetMapping("/auth/login")
	public String logingPage(@ModelAttribute("users") UsersDto users) throws MalformedURLException {
		
		return "auth/login";
	}
	
	@GetMapping("/newApplicantsPage")
	public String showApplicantsPage(Model model) {
		model.addAttribute("coursesList", courseService.getAll());
		return "showuserpage/applicants";
	}
}
