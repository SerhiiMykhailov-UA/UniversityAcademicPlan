package ua.foxminded.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ua.foxminded.dto.AdminDto;
import ua.foxminded.dto.StudentDto;
import ua.foxminded.dto.TeacherDto;
import ua.foxminded.dto.UsersDto;
import ua.foxminded.exceptions.AdminException;
import ua.foxminded.exceptions.StudentException;
import ua.foxminded.exceptions.TeacherException;
import ua.foxminded.exceptions.UsersException;
import ua.foxminded.security.UsersDetails;
import ua.foxminded.service.AdminService;
import ua.foxminded.service.StudentService;
import ua.foxminded.service.TeacherService;
import ua.foxminded.service.UsersService;

@Controller
public class UserPageController {

	private final UsersService usersService;
	private final AdminService adminService;
	private final StudentService studentService;
	private final TeacherService teacherService;
	
	public UserPageController(UsersService usersService, StudentService studentService, TeacherService teacherService, AdminService adminService) {
		this.usersService = usersService;
		this.adminService = adminService;
		this.studentService = studentService;
		this.teacherService = teacherService;
	}

	@GetMapping("/")
	public String start() {
		return "index";
	}
	
	@GetMapping("/showUserPage")
	public String showUserInfo(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UsersDetails details = (UsersDetails) authentication.getPrincipal();
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
				break;
			case "student":
				StudentDto studentDto = studentService.get(usersDto.getId());
				model.addAttribute("usersInfo", studentDto);
				break;
			case "teacher":
				TeacherDto teacherDto = teacherService.get(usersDto.getId());
				model.addAttribute("usersInfo", teacherDto);
				break;
			}
		} catch (UsersException | TeacherException | AdminException | StudentException e) {
			e.printStackTrace();
		}
		List<UsersDto> usersDtoList = usersService.getAll().stream()
				.sorted(Comparator.comparingLong(UsersDto::getId)).collect(Collectors.toList());
		model.addAttribute("usersDtoList", usersDtoList);

		return linkPage;
	}

}
