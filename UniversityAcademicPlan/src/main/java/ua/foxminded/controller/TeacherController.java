package ua.foxminded.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.foxminded.dto.CourseDto;
import ua.foxminded.dto.GroupsDto;
import ua.foxminded.dto.LectureDto;
import ua.foxminded.dto.ScheduleDto;
import ua.foxminded.dto.StudentDto;
import ua.foxminded.dto.TeacherDto;
import ua.foxminded.entity.UserType;
import ua.foxminded.exceptions.CourseException;
import ua.foxminded.exceptions.GroupsException;
import ua.foxminded.exceptions.TeacherException;
import ua.foxminded.service.CourseService;
import ua.foxminded.service.GroupsService;
import ua.foxminded.service.TeacherService;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

	private final TeacherService teacherService;
	private final CourseService courseService;
	private final GroupsService groupsService;
	
	private String userType = UserType.ROLE_TEACHER.getUserType();

	public TeacherController(TeacherService teacherService, CourseService courseService, GroupsService groupsService) {
		this.teacherService = teacherService;
		this.courseService = courseService;
		this.groupsService = groupsService;
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
			List<TeacherDto> teacherDtoList = courseDto.getTeacher();
			List<ScheduleDto> scheduleDtoList = courseDto.getSchedule();
			model.addAttribute("courseDto", courseDto);
			model.addAttribute("teacherDtoList", teacherDtoList);
			model.addAttribute("lectureDtoList", lectureDtoList);
			model.addAttribute("scheduleDtoList", scheduleDtoList);
			model.addAttribute("userType", userType);
		} catch (CourseException e) {
			e.printStackTrace();
		}
		return "course";
	}
	
	@GetMapping("/group/{id}")
	public String showGroupPage(@PathVariable("id") long id, Model model) {
		try {
			GroupsDto group = groupsService.get(id);
			model.addAttribute("studentGroupList", 
					group.getStudent()
					.stream()
					.sorted(Comparator.comparing(StudentDto::getFirstName))
					.collect(Collectors.toList()));
			model.addAttribute("group", group);
			model.addAttribute("userType", userType);
		} catch (GroupsException e) {
			e.printStackTrace();
		}
		return "group";
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
