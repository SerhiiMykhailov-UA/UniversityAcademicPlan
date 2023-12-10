package ua.foxminded.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.foxminded.dto.CourseDto;
import ua.foxminded.dto.LectureDto;
import ua.foxminded.dto.ScheduleDto;
import ua.foxminded.dto.TeacherDto;
import ua.foxminded.exceptions.CourseException;
import ua.foxminded.exceptions.TeacherException;
import ua.foxminded.service.CourseService;
import ua.foxminded.service.TeacherService;

@Controller
@RequestMapping("/teacherpage")
public class TeacherController {

	private final TeacherService teacherService;
	private final CourseService courseService;

	public TeacherController(TeacherService teacherService, CourseService courseService) {
		this.teacherService = teacherService;
		this.courseService = courseService;
	}
	
	@GetMapping()
	public String getAll (Model model) {
		model.addAttribute("teacherDtoList", teacherService.getAll());
		return "teacher/get_all";
	}
	
	@GetMapping("/{id}")
	public String get(@PathVariable("id") long id, Model model) throws TeacherException {
		TeacherDto teacherDto = teacherService.get(id);
		model.addAttribute("teacherDto", teacherDto);
		model.addAttribute("courseDtoList", teacherDto.getCourses());
		return "teacher/get";
	}
	
	@GetMapping("/course/{id}")
	public String courseTeacherPage(@PathVariable("id") long id, Model model) {
		try {
			CourseDto courseDto = courseService.get(id);
			List<LectureDto> lectureDtoList = courseDto.getLecture();
			List<ScheduleDto> scheduleDtoList = courseDto.getSchedule();
			model.addAttribute("lectureDtoList", lectureDtoList);
			model.addAttribute("courseDto", courseDto);
			model.addAttribute("scheduleDtoList", scheduleDtoList);
		} catch (CourseException e) {
			e.printStackTrace();
		}
		return "teacher/course";
	}
}
