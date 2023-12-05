package ua.foxminded.controller;

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
import ua.foxminded.dto.StudentDto;
import ua.foxminded.dto.TeacherDto;
import ua.foxminded.dto.UsersDto;
import ua.foxminded.entity.UserType;
import ua.foxminded.exceptions.StudentException;
import ua.foxminded.exceptions.TeacherException;
import ua.foxminded.exceptions.UsersException;
import ua.foxminded.service.AdminService;
import ua.foxminded.service.StudentService;
import ua.foxminded.service.TeacherService;
import ua.foxminded.service.UsersService;
import ua.foxminded.util.UsersDtoValidator;

@Controller
@RequestMapping("/adminpanel")
public class AdminController {

	private final UsersDtoValidator usersDtoValidator;
	private final UsersService usersService;
	private final AdminService adminService;
	private final StudentService studentService;
	private final TeacherService teacherService;
	
	public AdminController(UsersDtoValidator usersDtoValidator, UsersService usersService, TeacherService teacherService, StudentService studentService, AdminService adminService) {
		this.usersDtoValidator = usersDtoValidator;
		this.usersService = usersService;
		this.adminService = adminService;
		this.studentService = studentService;
		this.teacherService = teacherService;
	}
	
	@GetMapping("/registration")
	public String registrationPage(@ModelAttribute("users") UsersDto users) {
		return "adminpanel/registration";
	}
	
	@PostMapping("/registration")
	public String performRegistration(@ModelAttribute("users") @Valid UsersDto usersDto, BindingResult bindingResult) {
		usersDtoValidator.validate(usersDto, bindingResult);
		
		if (bindingResult.hasErrors())
			return "adminpanel/registration";
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
//		service.add(users);
		return "redirect:/showUserPage";
	}
	
	@GetMapping("/{id}")
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
	
	@PostMapping("/{id}")
	public String updateUsers(@PathVariable("id") long id, @ModelAttribute("usersDto") UsersDto usersDto) {
		try {
			usersService.update(usersDto);
		} catch (UsersException e) {
			e.getMessage();
		}
		return "redirect:/showUserPage";
	}
	
	@PostMapping("/delet")
	public String deletUser(@ModelAttribute("usersDto") UsersDto usersDto) {
		usersService.delete(usersDto.getId());
		return "redirect:/showUserPage";
	}
}
