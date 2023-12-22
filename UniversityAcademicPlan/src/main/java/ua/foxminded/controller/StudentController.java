package ua.foxminded.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.foxminded.dto.StudentDto;
import ua.foxminded.dto.TeacherDto;
import ua.foxminded.dto.CourseDto;
import ua.foxminded.dto.GroupsDto;
import ua.foxminded.dto.LectureDto;
import ua.foxminded.dto.ScheduleDto;
import ua.foxminded.exceptions.CourseException;
import ua.foxminded.exceptions.StudentException;
import ua.foxminded.service.CourseService;
import ua.foxminded.service.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController {

	private final StudentService studentService;
	private final CourseService courseService;

	public StudentController(StudentService studentService, CourseService courseService) {
		this.studentService = studentService;
		this.courseService = courseService;
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
		return "students/getStudent";
	}
	
	@PostMapping("/addAditionalCourse")
	public String addAditionalCourse(@ModelAttribute("studentDto") StudentDto studentDto, Errors errors) {
		try {
			StudentDto studentResult = studentService.get(studentDto.getId());
			CourseDto courseDto = courseService.getByName(studentDto.getCourse().get(0).getName());
			List<CourseDto> courseAdditionalList = studentResult.getCourse();
			if(!courseAdditionalList.contains(courseDto) & courseAdditionalList.size()<2) {
				courseAdditionalList.add(courseDto);
			}else {
				errors.rejectValue("course", "", "You are allready registered on this course, or course list is full");
				return "redirect:/showUserPage";
			}
			studentResult.setCourse(courseAdditionalList);
			studentService.update(studentResult);
		} catch (Exception e) {
			e.printStackTrace();
			errors.rejectValue("studentDto", "", e.getMessage() + " Contact administrator");
		}
		return "redirect:/showUserPage";
	}
	
	@PostMapping("/course/delet")
	public String deletAdditionalCourse(@ModelAttribute("studentDto") StudentDto studentDto, Errors errors) {
		System.out.println(12345);
			System.out.println(studentDto.getCourse());
		try {
			StudentDto studentResult = studentService.get(studentDto.getId());
			CourseDto courseDto = courseService.getByName(studentDto.getCourse().get(0).getName());
			List<CourseDto> courseAdditionalList = studentResult.getCourse();
			courseAdditionalList.remove(courseDto);
			studentResult.setCourse(courseAdditionalList);
			studentService.update(studentResult);
		} catch (Exception e) {
			e.printStackTrace();
			errors.rejectValue("studentDto", "", e.getMessage() + " Contact administrator");
		}
		return "redirect:/showUserPage";
	}
	
	@GetMapping("/course/{id}")
	public String showCoursePage (@PathVariable("id") long id, Model model) {
		try {
			CourseDto courseDto = courseService.get(id);
			List<LectureDto> lectureDtoList = courseDto.getLecture();
			List<TeacherDto> teacherDtoList = courseDto.getTeacher();
			List<ScheduleDto> scheduleDtoList = courseDto.getSchedule();
			model.addAttribute("courseDto", courseDto);
			model.addAttribute("teacherDtoList", teacherDtoList);
			model.addAttribute("lectureDtoList", lectureDtoList);
			model.addAttribute("scheduleDtoList", scheduleDtoList);
		} catch (CourseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/students/getCourse";
	}
	

	@GetMapping("/updateprofile")
	public String updateProfilePage(@RequestParam("id") long id, Model model) {
		try {
			StudentDto studentDto = studentService.get(id);
			model.addAttribute("userInfo", studentDto);
		} catch (StudentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "update_profile";
	}
	
	@PostMapping("/updateprofile")
	public String updateProfile(@ModelAttribute("userInfo") StudentDto studentDto) {
		try {
			StudentDto studentResult = studentService.get(studentDto.getId());
			studentResult.setFirstName(studentDto.getFirstName());
			studentResult.setLastName(studentDto.getLastName());
			studentService.update(studentResult);
		} catch (StudentException e) {
			e.printStackTrace();
		}
		return "redirect:/showUserPage";
	}
}
