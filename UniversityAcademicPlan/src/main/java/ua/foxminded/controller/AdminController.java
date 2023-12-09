package ua.foxminded.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import ua.foxminded.dto.AdminDto;
import ua.foxminded.dto.CourseDto;
import ua.foxminded.dto.LocationDto;
import ua.foxminded.dto.StudentDto;
import ua.foxminded.dto.TeacherDto;
import ua.foxminded.dto.UsersDto;
import ua.foxminded.exceptions.CourseException;
import ua.foxminded.exceptions.LocationException;
import ua.foxminded.exceptions.UsersException;
import ua.foxminded.service.AdminService;
import ua.foxminded.service.CourseService;
import ua.foxminded.service.LocationService;
import ua.foxminded.service.StudentService;
import ua.foxminded.service.TeacherService;
import ua.foxminded.service.UsersService;
import ua.foxminded.util.CourseDtoValidator;
import ua.foxminded.util.UsersDtoValidator;

@Controller
@RequestMapping("/adminpanel")
public class AdminController {

	private final UsersDtoValidator usersDtoValidator;
	private final CourseDtoValidator courseDtoValidator;
	private final UsersService usersService;
	private final AdminService adminService;
	private final StudentService studentService;
	private final TeacherService teacherService;
	private final CourseService courseService;
	private final LocationService locationService;
	
	public AdminController(UsersDtoValidator usersDtoValidator, UsersService usersService, TeacherService teacherService,
			StudentService studentService, AdminService adminService, CourseService courseService, LocationService locationService, CourseDtoValidator courseDtoValidator) {
		this.usersDtoValidator = usersDtoValidator;
		this.courseDtoValidator = courseDtoValidator;
		this.usersService = usersService;
		this.adminService = adminService;
		this.studentService = studentService;
		this.teacherService = teacherService;
		this.courseService = courseService;
		this.locationService = locationService;
	}
	
	@GetMapping("/user/registration")
	public String userRegistrationPage(@ModelAttribute("users") UsersDto users) {
		return "registration/user_registration";
	}
	
	@PostMapping("/user/registration")
	public String performUserRegistration(@ModelAttribute("users") @Valid UsersDto usersDto, BindingResult bindingResult) {
		usersDtoValidator.validate(usersDto, bindingResult);
		
		if (bindingResult.hasErrors())
			return "registration/user_registration";
		
		switch (usersDto.getUserType().getUserType()) {
		case "admin":
			AdminDto adminDto = new AdminDto("----", "----");
			adminDto.setId(usersDto.getId());
			adminDto.setNickName(usersDto.getNickName());
			adminDto.setPassword(usersDto.getPassword());
			adminDto.setUserType(usersDto.getUserType());
			adminService.add(adminDto);
			break;
		case "student":
			StudentDto studentDto = new StudentDto("----", "----");
			studentDto.setId(usersDto.getId());
			studentDto.setNickName(usersDto.getNickName());
			studentDto.setPassword(usersDto.getPassword());
			studentDto.setUserType(usersDto.getUserType());
			studentService.add(studentDto);
			break;
		case "teacher":
			TeacherDto teacherDto = new TeacherDto("----", "----");
			teacherDto.setId(usersDto.getId());
			teacherDto.setNickName(usersDto.getNickName());
			teacherDto.setPassword(usersDto.getPassword());
			teacherDto.setUserType(usersDto.getUserType());
			teacherService.add(teacherDto);
			break;
		}
		return "redirect:/showUserPage";
	}
	
	@GetMapping("/user/{id}")
	public String updateUsers(@PathVariable("id") long id, Model model) {
		UsersDto usersDto;
		try {
			usersDto = usersService.get(id);
			model.addAttribute("usersDto", usersDto);
		} catch (UsersException e) {
			e.printStackTrace();
		}
		return "adminpanel/users";
	}
	
	@PostMapping("/user/{id}")
	public String updateUsers(@PathVariable("id") long id, @ModelAttribute("usersDto") UsersDto usersDto) {
		try {
			usersService.update(usersDto);
		} catch (UsersException e) {
			e.getMessage();
		}
		return "redirect:/showUserPage";
	}
	
	@PostMapping("/user/delet")
	public String deletUser(@ModelAttribute("usersDto") UsersDto usersDto) {
		usersService.delete(usersDto.getId());
		return "redirect:/showUserPage";
	}
	
	@GetMapping("/course/{id}")
	public String updateCourse(@PathVariable("id") long id, Model model) {
		CourseDto courseDto;
		try {
			courseDto = courseService.get(id);
			List<LocationDto> locationDtoList = locationService.getAll();
			model.addAttribute("courseDto", courseDto);
			model.addAttribute("locationDtoList", locationDtoList);
		} catch (CourseException e) {
			e.printStackTrace();
		}
		return "adminpanel/course";
	}
	
	@PostMapping("/course/{id}")
	public String updateCourse(@PathVariable("id") long id, @ModelAttribute("courseDto") CourseDto courseDto) {
		try {
			courseService.update(courseDto);
		} catch (CourseException | LocationException e) {
			e.getMessage();
		}
		return "redirect:/showUserPage";
	}
	
	@PostMapping("/course/delet")
	public String deletCourse(@ModelAttribute("courseDto") CourseDto courseDto) {
		courseService.delete(courseDto.getId());
		return "redirect:/showUserPage";
	}
	
	@GetMapping("/course/registration")
	public String courseRegistrationPage(@ModelAttribute("course") CourseDto course, Model model) {
		List<LocationDto> locationDtoList = locationService.getAll();
		model.addAttribute("locationDtoList", locationDtoList);
		return "registration/course_registration";
	}
	
	@PostMapping("/course/registration")
	public String performCourseRegistration(@ModelAttribute("course") CourseDto course, BindingResult bindingResult, Model model) {
		List<LocationDto> locationDtoList = locationService.getAll();
		model.addAttribute("locationDtoList", locationDtoList);
		courseDtoValidator.validate(course, bindingResult);
		
		if (bindingResult.hasErrors())
			return "registration/course_registration";
		try {
			courseService.add(course);
		} catch (LocationException e) {
			bindingResult.rejectValue("course", "", "Add course error. Contact the Administrator");
			return "registration/course_registration";
		}
		return "redirect:/showUserPage";
	}
}
