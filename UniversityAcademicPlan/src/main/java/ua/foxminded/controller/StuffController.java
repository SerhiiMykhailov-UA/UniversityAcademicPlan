package ua.foxminded.controller;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.foxminded.dto.CourseDto;
import ua.foxminded.dto.GroupsDto;
import ua.foxminded.dto.LectureDto;
import ua.foxminded.dto.LocationDto;
import ua.foxminded.dto.ScheduleDto;
import ua.foxminded.dto.StuffDto;
import ua.foxminded.dto.TeacherDto;
import ua.foxminded.entity.UserType;
import ua.foxminded.exceptions.CourseException;
import ua.foxminded.exceptions.GroupsException;
import ua.foxminded.exceptions.LectureException;
import ua.foxminded.exceptions.LocationException;
import ua.foxminded.exceptions.ScheduleException;
import ua.foxminded.exceptions.StuffException;
import ua.foxminded.exceptions.TeacherException;
import ua.foxminded.service.CourseService;
import ua.foxminded.service.GroupsService;
import ua.foxminded.service.LectureService;
import ua.foxminded.service.LocationService;
import ua.foxminded.service.ScheduleService;
import ua.foxminded.service.StuffService;
import ua.foxminded.service.TeacherService;
import ua.foxminded.util.CourseDtoValidator;

@Controller
@RequestMapping("/stuff")
public class StuffController {
	
	private final CourseDtoValidator courseDtoValidator;
	private final StuffService stuffService;
	private final CourseService courseService;
	private final TeacherService teacherService;
	private final GroupsService groupsService;
	private final LocationService locationService;
	private final ScheduleService scheduleService;
	private final LectureService lectureService;
	
	private String userType = UserType.ROLE_STUFF.getUserType();
	
	public StuffController(CourseDtoValidator courseDtoValidator,
			StuffService stuffService, CourseService courseService,
			LocationService locationService, TeacherService teacherService,
			GroupsService groupsService, ScheduleService scheduleService, LectureService lectureService) {
		this.courseDtoValidator = courseDtoValidator;
		this.stuffService = stuffService;
		this.courseService = courseService;
		this.teacherService = teacherService;
		this.groupsService = groupsService;
		this.locationService = locationService;
		this.scheduleService = scheduleService;
		this.lectureService = lectureService;
	}

	@GetMapping("/course/{id}")
	public String updateCourse(@ModelAttribute("lecture") LectureDto lecture, @PathVariable("id") long id, Model model) {
		CourseDto courseDto;
		try {
			courseDto = courseService.get(id);
			List<LocationDto> locationDtoList = locationService.getAll();
			List<LectureDto> lectureDtoList = courseDto.getLecture()
					.stream()
					.sorted(Comparator.comparingLong(el->el.getId()))
					.collect(Collectors.toList());
			List<TeacherDto> teacherDtoList = courseDto.getTeacher();
			List<TeacherDto> teacherLeftList = teacherService.getAll()
					.stream()
					.filter(el->!teacherDtoList.contains(el))
					.collect(Collectors.toList());
			List<GroupsDto> groupsDtoList = courseDto.getGroups();
			List<GroupsDto> groupsLeftList = groupsService.getAll()
					.stream()
					.filter(el->!groupsDtoList.contains(el))
					.collect(Collectors.toList());
			List<ScheduleDto> scheduleDtoList = courseDto.getSchedule();
			List<ScheduleDto> scheduleLeftList = scheduleService.getAll()
					.stream()
					.filter(el->!scheduleDtoList.contains(el))
					.collect(Collectors.toList());
			model.addAttribute("courseDto", courseDto);
			model.addAttribute("locationDtoList", locationDtoList);
			model.addAttribute("lectureDtoList", lectureDtoList);
			model.addAttribute("teacherDtoList", teacherDtoList);
			model.addAttribute("teacherLeftList", teacherLeftList);
			model.addAttribute("groupsDtoList", groupsDtoList);
			model.addAttribute("groupsLeftList", groupsLeftList);
			model.addAttribute("scheduleDtoList", scheduleDtoList);
			model.addAttribute("scheduleLeftList", scheduleLeftList);
			model.addAttribute("userType", userType);
		} catch (CourseException e) {
			e.printStackTrace();
		}
		return "course";
	}
	
	@PostMapping("/course/{id}")
	public String updateCourse(@PathVariable("id") long id, @ModelAttribute("courseDto") CourseDto courseDto) {
		try {
			CourseDto courseDtoTemp = courseService.get(courseDto.getId());
			courseDtoTemp.setName(courseDto.getName());
			courseDtoTemp.setLocation(courseDto.getLocation());
			
			courseService.update(courseDtoTemp);
			
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
	
	@PostMapping("/addTeacherToCourse")
	public String addTeacherToCourse(@ModelAttribute("courseDto") CourseDto course, @ModelAttribute("teacherDto") TeacherDto teacher, Errors errors) {
		String[] s = teacher.getFirstName().split(" ");
		String firstName = s[0];
		String lastName = s[1];
		try {
			TeacherDto teacherDtoTemp = teacherService.getByFirstNameAndLastName(firstName, lastName);
			CourseDto courseDtoTemp = courseService.get(course.getId());
			List<TeacherDto> teacherDtoList = courseDtoTemp.getTeacher();
			teacherDtoList.add(teacherDtoTemp);
			courseDtoTemp.setTeacher(teacherDtoList);
			
			courseService.update(courseDtoTemp);
			
		} catch (TeacherException | CourseException | LocationException e) {
			e.printStackTrace();
			errors.rejectValue("courseDto", "", e.getMessage() + " Contact administrator");
			return "redirect:/stuff/course/" + course.getId();
		}
		return "redirect:/stuff/course/" + course.getId();
	}
	
	@PostMapping("/addGroupToCourse")
	public String addGroupsToCourse(@ModelAttribute("courseDto") CourseDto courseDto, Errors errors) {
		try {
			GroupsDto groupsDto = groupsService.getByName(courseDto.getGroups().get(0).getName());
			CourseDto courseDtoTemp = courseService.get(courseDto.getId());
			List<CourseDto> courseDtoList = groupsDto.getCourse();
			courseDtoList.add(courseDtoTemp);
			groupsDto.setCourse(courseDtoList);
			
			groupsService.update(groupsDto);
			
		} catch (CourseException | GroupsException  e) {
			e.printStackTrace();
			errors.rejectValue("courseDto", "", e.getMessage() + " Contact administrator");
			return "redirect:/stuff/course/" + courseDto.getId();
		}
		
		return "redirect:/stuff/course/" + courseDto.getId();
	}
	
	@PostMapping("/addScheduleToCourse")
	public String addScheduleToCourse(@ModelAttribute("scheduleDto") ScheduleDto schedule, @ModelAttribute("courseDto") CourseDto course, Errors errors) {
		String[] s = schedule.getNameSchedule().split(" ");
		LocalTime startTime = LocalTime.parse(s[0]);
		LocalTime endTime = LocalTime.parse(s[1]);
		DayOfWeek dayOfWeek = DayOfWeek.valueOf(s[2]);
		try {
			ScheduleDto scheduleDto = scheduleService.getByStartTimeAndEndTimeAndDayOfWeek(startTime, endTime, dayOfWeek);
			CourseDto courseDtoResult = courseService.get(course.getId());
			System.out.println(scheduleDto);
			System.out.println(courseDtoResult);
			List<CourseDto> courseDtoList = scheduleDto.getCourse();
			courseDtoList.add(courseDtoResult);
			scheduleDto.setCourse(courseDtoList);

			scheduleService.update(scheduleDto);
			
		} catch (ScheduleException | CourseException e) {
			e.printStackTrace();
		}
		return "redirect:/stuff/course/" + course.getId();
	}
	
	@PostMapping("/addLectureToCourse")
	public String addLectureToCourse (@ModelAttribute("lecture") LectureDto lecture, @ModelAttribute("courseDto") CourseDto course, Errors errors) {
		try {
			CourseDto courseDtoTemp = courseService.get(course.getId());
			LectureDto lectureDtoTemp = new LectureDto(lecture.getName());
			
			LectureDto lectureDtoResult = lectureService.add(lectureDtoTemp);
			lectureDtoResult.setCourse(courseDtoTemp);
			
			lectureService.update(lectureDtoResult);
			
		} catch (CourseException | LectureException e) {
			e.printStackTrace();
		}
		return "redirect:/stuff/course/" + course.getId();
	}
	
	@PostMapping("/deletGroupFromCourse")
	public String deleteGroupFromCourse(@ModelAttribute("courseDto") CourseDto courseDto, Errors errors) {
		try {
			GroupsDto groupsDto = groupsService.getByName(courseDto.getGroups().get(0).getName());
			CourseDto courseDtoTemp = courseService.get(courseDto.getId());
			List<CourseDto> courseDtoList = groupsDto.getCourse();
			courseDtoList.remove(courseDtoTemp);
			groupsDto.setCourse(courseDtoList);
			
			groupsService.update(groupsDto);
			
		} catch (GroupsException | CourseException e) {
			e.printStackTrace();
			errors.rejectValue("courseDto", "", e.getMessage() + " Contact administrator");
			return "redirect:/stuff/course/" + courseDto.getId();
		}
		return "redirect:/stuff/course/" + courseDto.getId();
	}
	
	@PostMapping("/deletTeacherFromCourse")
	public String deleteTeacherFromCourse(@ModelAttribute("teacher") TeacherDto teacher, @ModelAttribute("course") CourseDto course, Errors errors) {
		String[] s = teacher.getFirstName().split(" ");
		String firstName = s[0];
		String lastName = s[1];
		try {
			TeacherDto teacherDtoTemp = teacherService.getByFirstNameAndLastName(firstName, lastName);
			CourseDto courseDtoTemp = courseService.get(course.getId());
			List<TeacherDto> teacherDtoList = courseDtoTemp.getTeacher();
			teacherDtoList.remove(teacherDtoTemp);
			courseDtoTemp.setTeacher(teacherDtoList);
			
			courseService.update(courseDtoTemp);
			
		} catch (TeacherException | CourseException | LocationException e) {
			e.printStackTrace();
		}
		return "redirect:/stuff/course/" + course.getId();
	}
	
	@PostMapping("/deletScheduleFromCourse")
	public String deleteScheduleFrobCourse(@ModelAttribute("schedule") ScheduleDto schedule, @ModelAttribute("course") CourseDto course, Errors errors) {
		try {
			CourseDto courseDtoTemp = courseService.getByName(course.getName());
			ScheduleDto scheduleDtoTemp = scheduleService.get(Long.parseLong(schedule.getNameSchedule()));
			List<CourseDto> courseDtoList = scheduleDtoTemp.getCourse();
			courseDtoList.remove(courseDtoTemp);
			scheduleDtoTemp.setCourse(courseDtoList);
			
			scheduleService.update(scheduleDtoTemp);
			
		} catch (CourseException | ScheduleException e) {
			e.printStackTrace();
		}
		return "redirect:/stuff/course/" + course.getId();
	}
	
	@PostMapping("/deleteLectureFromCourse")
	public String deleteLectureFromCoures(@ModelAttribute("lecture") LectureDto lecture, @ModelAttribute("courseDto") CourseDto course, Errors errors) {
		try {
			LectureDto lectureDtoTemp = lectureService.getByName(lecture.getName());
			lectureDtoTemp.setCourse(null);
			
			lectureService.update(lectureDtoTemp);
			lectureService.delete(lectureDtoTemp.getId());
			
		} catch (LectureException e) {
			e.printStackTrace();
		}
		return "redirect:/stuff/course/" + course.getId();
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
			bindingResult.rejectValue("courseError", "", "Add course error. Contact the Administrator");
			return "registration/course_registration";
		}
		return "redirect:/showUserPage";
	}
	
	@GetMapping("/updateprofile")
	public String updateProfilePage(@RequestParam("id") long id, Model model) {
		try {
			StuffDto stuffDto = stuffService.get(id);
			model.addAttribute("userInfo", stuffDto);
		} catch (StuffException e) {
			e.printStackTrace();
		}
		return "update_profile";
	}
	
	@PostMapping("/updateprofile")
	public String updateProfile(@ModelAttribute("userInfo") StuffDto stuff) {
		try {
			StuffDto stuffResult = stuffService.get(stuff.getId());
			stuffResult.setFirstName(stuff.getFirstName());
			stuffResult.setLastName(stuff.getLastName());
			stuffService.update(stuffResult);
		} catch (StuffException e) {
			e.printStackTrace();
		}
		return "redirect:/showUserPage";
	}
	
	@GetMapping("/updatePassword")
	public String updatePasswordGet(@RequestParam("id") long id, Model model) {
		try {
			StuffDto stuffDto = stuffService.get(id);
			model.addAttribute("userInfo", stuffDto);
		} catch (StuffException e) {
			e.printStackTrace();
		}
		return "update_password";
	}
	
	@PostMapping("/updatePassword")
	public String updatePasswordPost(@ModelAttribute("userInfo") StuffDto stuff) {
		try {
			StuffDto stuffResult = stuffService.get(stuff.getId());
			stuffResult.setPassword(stuff.getPassword());
			stuffService.update(stuffResult);
		} catch (StuffException e) {
			e.printStackTrace();
		}
		return "redirect:/showUserPage";
	}
}
