package ua.foxminded.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
	
	@GetMapping("/hello-world")
	public String sayHello() {
		return "hello_world";
	}
	
	@GetMapping("/goodbay")
	public String saygoodbye() {
		return "goodbay_world_viaw";
	}
	
}
