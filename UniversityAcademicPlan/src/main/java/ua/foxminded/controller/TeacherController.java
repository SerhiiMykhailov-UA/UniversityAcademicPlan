package ua.foxminded.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.foxminded.dto.TeacherDto;
import ua.foxminded.exceptions.TeacherException;
import ua.foxminded.service.TeacherService;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

	private TeacherService teacherService;

	public TeacherController(TeacherService teacherService) {
		this.teacherService = teacherService;
	}
	
	@GetMapping()
	public String getAll (Model model) {
		model.addAttribute("teacherDtoList", teacherService.getAll());
		return "teachers/get_all";
	}
	
	@GetMapping("/{id}")
	public String get(@PathVariable("id") long id, Model model) throws TeacherException {
		TeacherDto teacherDto = teacherService.get(id);
		model.addAttribute("teacherDto", teacherDto);
		model.addAttribute("courseDtoList", teacherDto.getCourses());
		return "teachers/get";
	}
}
