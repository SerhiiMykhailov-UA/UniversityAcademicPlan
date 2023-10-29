package ua.foxminded.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.foxminded.dto.StudentDto;
import ua.foxminded.dto.CourseDto;
import ua.foxminded.dto.GroupsDto;
import ua.foxminded.exceptions.StudentException;
import ua.foxminded.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {

	private StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}
	
	@GetMapping()
	public String getAll (Model model) {
		model.addAttribute("studentDtoList", studentService.getAll());
		return "students/get_all";
	}
	
	@GetMapping("/{id}")
	public String get (@PathVariable("id") long id, Model model) throws StudentException {
		StudentDto studentDto = studentService.get(id);
		GroupsDto groupsDto = studentDto.getGroups();
		List<CourseDto> courseDtoStidentsList = groupsDto.getCourse();
		courseDtoStidentsList.addAll(studentDto.getCourse());
		model.addAttribute("studentDto", studentDto);
		model.addAttribute("groupsDto", groupsDto);
		model.addAttribute("courseDtoList", courseDtoStidentsList);
		return "students/get";
	}
}
