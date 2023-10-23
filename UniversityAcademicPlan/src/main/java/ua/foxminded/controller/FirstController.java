package ua.foxminded.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/first")
public class FirstController {

	@GetMapping("/hello")
	public String helloPage (HttpServletRequest request, Model model) {
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
//		System.out.println("First " + name + " " + surname);
		model.addAttribute("message", "Hello " + name + " " + surname);
		return "first/hello";
	}
	
	@GetMapping("/goodbye")
	public String goodbyePage (@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "surname", required = false) String surname, Model model) {
		System.out.println("Second " + name + " " + surname);

		model.addAttribute("message", "Goodbye " + name + " " + surname);
		return "first/goodbye";
	}
	
	@GetMapping("/calculator")
	public String calculatorPage (@RequestParam (value = "a", required = false) int a,
			@RequestParam(value = "b", required = false) int b,
			@RequestParam(value = "action", required = false) String action, Model model) {
		double c;
		switch (action) {
		case "multiplication":
			action = " * ";
			c = a * b;
			break;
		case "addition":
			c = a + b;
			action = " + ";
			break;
		case "substraction":
			action = " - ";
			c = a - b;
			break;
		case "division":
			action = " / ";
			c = a / (double)b;
			break;

		default:
			c = 0;
			break;
		}
		model.addAttribute("result", a + action + b + " = " + c);
		return "first/calculator";
	}
}
