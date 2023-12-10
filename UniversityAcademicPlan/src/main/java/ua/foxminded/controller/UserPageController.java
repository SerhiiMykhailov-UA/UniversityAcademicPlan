package ua.foxminded.controller;

import java.security.InvalidParameterException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ua.foxminded.dto.AdminDto;
import ua.foxminded.dto.CourseDto;
import ua.foxminded.dto.StudentDto;
import ua.foxminded.dto.TeacherDto;
import ua.foxminded.dto.UsersDto;
import ua.foxminded.exceptions.AdminException;
import ua.foxminded.exceptions.StudentException;
import ua.foxminded.exceptions.TeacherException;
import ua.foxminded.exceptions.UsersException;
import ua.foxminded.security.UsersDetails;
import ua.foxminded.service.AdminService;
import ua.foxminded.service.CourseService;
import ua.foxminded.service.StudentService;
import ua.foxminded.service.TeacherService;
import ua.foxminded.service.UsersService;

@Controller
public class UserPageController {

	private final UsersService usersService;
	private final AdminService adminService;
	private final StudentService studentService;
	private final TeacherService teacherService;
	private final CourseService courseService;
	
	public UserPageController(UsersService usersService, StudentService studentService,
			TeacherService teacherService, AdminService adminService, CourseService courseService) {
		this.usersService = usersService;
		this.adminService = adminService;
		this.studentService = studentService;
		this.teacherService = teacherService;
		this.courseService = courseService;
	}

	@GetMapping("/")
	public String start() {
		return "index";
	}
	
	@GetMapping("/showUserPage")
	public String showUserInfo(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UsersDetails details;
		if (authentication.getPrincipal() instanceof UsersDetails) {
		details = (UsersDetails) authentication.getPrincipal();
		} else if (authentication.getPrincipal() instanceof User) {
		details = new UsersDetails((UsersDto) authentication.getPrincipal());
		} else {
		throw new InvalidParameterException();
		}
		model.addAttribute("usersDetails", details);
		String linkPage = "showuserpage/";
		try {
			UsersDto usersDto = usersService.getByNickName(details.getUsername());
			model.addAttribute("usersDto", usersDto);
			linkPage = linkPage + usersDto.getUserType().getUserType();
			switch (usersDto.getUserType().getUserType()) {
			case "admin":
				AdminDto adminDto = adminService.get(usersDto.getId());
				model.addAttribute("usersInfo", adminDto);
				List<UsersDto> usersDtoList = usersService.getAll().stream()
						.sorted(Comparator.comparingLong(UsersDto::getId)).collect(Collectors.toList());
				model.addAttribute("usersDtoList", usersDtoList);
				List<CourseDto> courseDtoList = courseService.getAll().stream()
						.sorted(Comparator.comparingLong(CourseDto::getId)).collect(Collectors.toList());
				model.addAttribute("courseDtoList", courseDtoList);
				break;
			case "student":
				StudentDto studentDto = studentService.get(usersDto.getId());
				model.addAttribute("usersInfo", studentDto);
				break;
			case "teacher":
				TeacherDto teacherDto = teacherService.get(usersDto.getId());
				model.addAttribute("usersInfo", teacherDto);
				List<CourseDto> courseTeacherList = teacherDto.getCourses();
				model.addAttribute("courseTeacherList", courseTeacherList);
				break;
			}
		} catch (UsersException | TeacherException | AdminException | StudentException e) {
			e.printStackTrace();
		}
		return linkPage;
	}

}
