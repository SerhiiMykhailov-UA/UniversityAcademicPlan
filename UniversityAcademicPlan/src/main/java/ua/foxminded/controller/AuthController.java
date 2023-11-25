package ua.foxminded.controller;

import java.net.MalformedURLException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.foxminded.dto.UsersDto;

@Controller
@RequestMapping("/auth")
public class AuthController {

	
	@GetMapping("/login")
	public String logingPage(@ModelAttribute("users") UsersDto users) throws MalformedURLException {
		return "auth/login";
	}
}
