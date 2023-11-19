package ua.foxminded.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.foxminded.dto.CourseDto;
import ua.foxminded.dto.GroupsDto;
import ua.foxminded.dto.StudentDto;
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
		model.addAttribute("coursesDtoList", courseService.getAll());
		return "courses/get_all";
	}
	
	@GetMapping("/{id}")
	public String get (@PathVariable("id") long id, Model model) throws CourseException {
		CourseDto courseDto = courseService.get(id);
		List<GroupsDto> groupsList = courseDto.getGroups();
		List<StudentDto> studentsList = new ArrayList<>();
		for (GroupsDto groups:groupsList) {
			List<StudentDto> studentsInGroups = groups.getStudent();
			studentsList.addAll(studentsInGroups);
		}
		studentsList.addAll(courseDto.getStudent());
		model.addAttribute("courseDto", courseDto);
		model.addAttribute("groupsDtoList", groupsList);
		model.addAttribute("studentsDtoList", studentsList);
		model.addAttribute("scheduleDtoList", courseDto.getSchedule());
		model.addAttribute("teacherDtoList", courseDto.getTeacher());
		model.addAttribute("lectureDtoList", courseDto.getLecture());
		return "courses/get";
	}
}
