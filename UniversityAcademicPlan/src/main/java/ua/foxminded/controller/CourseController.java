package ua.foxminded.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.foxminded.dto.CourseDto;
import ua.foxminded.exceptions.CourseException;
import ua.foxminded.service.CourseService;

@Controller
@RequestMapping("/courses")
public class CourseController {

	private CourseService courseService;
	
	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}

	@GetMapping()
	public String getAll (Model model) {
		List<CourseDto> list = courseService.getAll();
		System.out.println(list);
		model.addAttribute("coursesDtoList", courseService.getAll());
		return "courses/get_all";
	}
	
	@GetMapping("/{id}")
	public String get (@PathVariable("id") long id, Model model) throws CourseException {
		model.addAttribute("courseDto", courseService.get(id));
		return "course/get";
	}
}
