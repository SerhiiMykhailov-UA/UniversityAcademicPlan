package ua.foxminded.controller;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import ua.foxminded.dto.AdminDto;
import ua.foxminded.dto.CourseDto;
import ua.foxminded.dto.GroupsDto;
import ua.foxminded.dto.ScheduleDto;
import ua.foxminded.dto.StudentDto;
import ua.foxminded.dto.TeacherDto;
import ua.foxminded.dto.UsersDto;
import ua.foxminded.dto.StuffDto;
import ua.foxminded.exceptions.AdminException;
import ua.foxminded.exceptions.GroupsException;
import ua.foxminded.exceptions.StudentException;
import ua.foxminded.exceptions.StuffException;
import ua.foxminded.exceptions.TeacherException;
import ua.foxminded.exceptions.UsersException;
import ua.foxminded.security.UsersDetails;
import ua.foxminded.service.AdminService;
import ua.foxminded.service.CourseService;
import ua.foxminded.service.GroupsService;
import ua.foxminded.service.ScheduleService;
import ua.foxminded.service.StudentService;
import ua.foxminded.service.StuffService;
import ua.foxminded.service.TeacherService;
import ua.foxminded.service.UsersService;

@Controller
public class UserPageController {

	private final UsersService usersService;
	private final AdminService adminService;
	private final StudentService studentService;
	private final TeacherService teacherService;
	private final StuffService stuffService;
	private final CourseService courseService;
	private final GroupsService groupsService;
	private final ScheduleService scheduleService;

	public UserPageController(UsersService usersService, StudentService studentService, TeacherService teacherService,
			AdminService adminService, CourseService courseService, StuffService stuffService, GroupsService groupsService, ScheduleService scheduleService) {
		this.usersService = usersService;
		this.adminService = adminService;
		this.studentService = studentService;
		this.teacherService = teacherService;
		this.stuffService = stuffService;
		this.courseService = courseService;
		this.groupsService = groupsService;
		this.scheduleService = scheduleService;
	}

	@GetMapping("/")
	public String start() {
		return "index";
	}

	@GetMapping("/showUserPage")
	public String showUserInfo(@ModelAttribute("schedule") ScheduleDto schedule ,@ModelAttribute("group") GroupsDto groups, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UsersDetails details;
		if (authentication.getPrincipal() instanceof UsersDetails) {
			details = (UsersDetails) authentication.getPrincipal();
		} else if (authentication.getPrincipal() instanceof User) {
			details = new UsersDetails((User) authentication.getPrincipal());
		} else {
			throw new InvalidParameterException();
		}
		model.addAttribute("usersDetails", details);
		String linkPage = "showuserpage/";
		try {
			UsersDto usersDto = usersService.getByNickName(details.getUsername());
			model.addAttribute("usersDto", usersDto);
			List<CourseDto> courseDtoList = courseService.getAll().stream()
					.sorted(Comparator.comparing(CourseDto::getName))
					.collect(Collectors.toList());
			List<GroupsDto> groupsDtoList = groupsService.getAll()
					.stream()
					.sorted(Comparator.comparing(GroupsDto::getName))
					.collect(Collectors.toList());
			List<ScheduleDto> scheduleDtoList = scheduleService.getAll()
					.stream()
					.sorted(Comparator.comparing(ScheduleDto::getDayOfWeek))
					.collect(Collectors.toList());
			model.addAttribute("courseDtoList", courseDtoList);
			model.addAttribute("groupsDtoList", groupsDtoList);
			model.addAttribute("scheduleDtoList", scheduleDtoList);
			linkPage = linkPage + usersDto.getUserType().getUserType();
			switch (usersDto.getUserType().getUserType()) {
			case "admin":
				AdminDto adminDto = adminService.get(usersDto.getId());
				model.addAttribute("usersInfo", adminDto);
				List<UsersDto> usersDtoList = usersService.getAll().stream()
						.sorted(Comparator.comparingLong(UsersDto::getId)).collect(Collectors.toList());
				model.addAttribute("usersDtoList", usersDtoList);
				break;
			case "student":
				StudentDto studentDto = studentService.get(usersDto.getId());
				model.addAttribute("studentDto", studentDto);
				List<CourseDto> courseDtoGroup = new ArrayList<>();
				List<CourseDto> courseStudentList = new ArrayList<>();
				List<CourseDto> courseDtoAdditional = new ArrayList<>();
				if (studentDto.getGroups() != null) {
					GroupsDto groupsDto = groupsService.get(studentDto.getGroups().getId());
					courseDtoGroup = groupsDto.getCourse().stream()
							.sorted(Comparator.comparingLong(CourseDto::getId)).collect(Collectors.toList());
					courseStudentList = studentDto.getCourse().stream()
							.sorted(Comparator.comparingLong(CourseDto::getId)).collect(Collectors.toList());
					for (CourseDto courseDto : courseDtoList) {
						if (!courseDtoGroup.contains(courseDto) & !courseStudentList.contains(courseDto))
							courseDtoAdditional.add(courseDto);
					}
				}
					model.addAttribute("courseGroupList", courseDtoGroup);
					model.addAttribute("courseStudentList", courseStudentList);
					model.addAttribute("courseDtoAdditional", courseDtoAdditional);
				break;
			case "teacher":
				TeacherDto teacherDto = teacherService.get(usersDto.getId());
				model.addAttribute("usersInfo", teacherDto);
				List<CourseDto> courseTeacherList = teacherDto.getCourses();
				model.addAttribute("courseTeacherList", courseTeacherList);
				break;
			case "stuff":
				StuffDto stuffDto = stuffService.get(usersDto.getId());
				model.addAttribute("usersInfo", stuffDto);
				break;
			}
		} catch (UsersException | TeacherException | AdminException | StudentException | GroupsException | StuffException e) {
			e.printStackTrace();
		}
		return linkPage;
	}

}
