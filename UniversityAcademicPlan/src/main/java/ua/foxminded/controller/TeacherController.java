package ua.foxminded.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.foxminded.dto.AdminDto;
import ua.foxminded.dto.CourseDto;
import ua.foxminded.dto.LectureDto;
import ua.foxminded.dto.ScheduleDto;
import ua.foxminded.dto.TeacherDto;
import ua.foxminded.exceptions.AdminException;
import ua.foxminded.exceptions.CourseException;
import ua.foxminded.exceptions.TeacherException;
import ua.foxminded.service.CourseService;
import ua.foxminded.service.TeacherService;

@Controller
@RequestMapping("/teacher")
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
	
	@GetMapping("/updateprofile")
	public String updateProfilePage(@RequestParam("id") long id, Model model) {
		try {
			TeacherDto teacherDto = teacherService.get(id);
			model.addAttribute("userInfo", teacherDto);
		} catch (TeacherException e) {
			e.printStackTrace();
		}
		return "update_profile";
	}
	
	@PostMapping("/updateprofile")
	public String updateProfile(@ModelAttribute("userInfo") TeacherDto teacherDto) {
		try {
			TeacherDto teacherResult = teacherService.get(teacherDto.getId());
			teacherResult.setFirstName(teacherDto.getFirstName());
			teacherResult.setLastName(teacherDto.getLastName());
			teacherService.update(teacherResult);
		} catch (TeacherException e) {
			e.printStackTrace();
		}
		return "redirect:/showUserPage";
	}
	
	@GetMapping("/updatePassword")
	public String updatePasswordGet(@RequestParam("id") long id, Model model) {
		try {
			TeacherDto teacherDto = teacherService.get(id);
			model.addAttribute("userInfo", teacherDto);
		} catch (TeacherException e) {
			e.printStackTrace();
		}
		return "update_password";
	}
	
	@PostMapping("/updatePassword")
	public String updatePasswordPost(@ModelAttribute("userInfo") TeacherDto teacherDto) {
		try {
			TeacherDto teacherResult = teacherService.get(teacherDto.getId());
			teacherResult.setPassword(teacherDto.getPassword());
			teacherService.update(teacherResult);
		} catch (TeacherException e) {
			e.printStackTrace();
		}
		return "redirect:/showUserPage";
	}
}
