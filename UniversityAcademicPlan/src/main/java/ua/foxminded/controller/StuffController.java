package ua.foxminded.controller;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
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
import ua.foxminded.dto.StudentDto;
import ua.foxminded.dto.StuffDto;
import ua.foxminded.dto.TeacherDto;
import ua.foxminded.entity.UserType;
import ua.foxminded.exceptions.CourseException;
import ua.foxminded.exceptions.GroupsException;
import ua.foxminded.exceptions.LectureException;
import ua.foxminded.exceptions.LocationException;
import ua.foxminded.exceptions.ScheduleException;
import ua.foxminded.exceptions.StudentException;
import ua.foxminded.exceptions.StuffException;
import ua.foxminded.exceptions.TeacherException;
import ua.foxminded.service.CourseService;
import ua.foxminded.service.GroupsService;
import ua.foxminded.service.LectureService;
import ua.foxminded.service.LocationService;
import ua.foxminded.service.ScheduleService;
import ua.foxminded.service.StudentService;
import ua.foxminded.service.StuffService;
import ua.foxminded.service.TeacherService;
import ua.foxminded.util.CourseDtoValidator;
import ua.foxminded.util.GroupsDtoValidator;

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
	private final GroupsDtoValidator groupsDtoValidator;
	private final StudentService studentService;
	
	private String userType = UserType.ROLE_STUFF.getUserType();
	
	public StuffController(CourseDtoValidator courseDtoValidator,
			StuffService stuffService, CourseService courseService,
			LocationService locationService, TeacherService teacherService,
			GroupsService groupsService, ScheduleService scheduleService, LectureService lectureService, GroupsDtoValidator groupsDtoValidator, StudentService studentService) {
		this.courseDtoValidator = courseDtoValidator;
		this.stuffService = stuffService;
		this.courseService = courseService;
		this.teacherService = teacherService;
		this.groupsService = groupsService;
		this.locationService = locationService;
		this.scheduleService = scheduleService;
		this.lectureService = lectureService;
		this.groupsDtoValidator = groupsDtoValidator;
		this.studentService = studentService;
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
	
	@GetMapping("/group/{id}")
	public String updateGroup(@PathVariable("id") long id, Model model) {
		GroupsDto groupsDto;
		try {
			groupsDto = groupsService.get(id);
			List<StudentDto> studentLeftList = studentService.getAll()
					.stream()
					.filter(el->el.getGroups() == null)
					.collect(Collectors.toList());
			List<CourseDto> courseGroupsList = groupsDto.getCourse();
			List<CourseDto> courseLeftList = courseService.getAll()
					.stream()
					.filter(el->!courseGroupsList.contains(el))
					.collect(Collectors.toList());
			List<StudentDto> studentGroupList = groupsDto.getStudent()
					.stream()
					.sorted(Comparator.comparing(StudentDto::getFirstName))
					.collect(Collectors.toList());
			model.addAttribute("group", groupsDto);
			model.addAttribute("studentLeftList", studentLeftList);
			model.addAttribute("courseLeftList", courseLeftList);
			model.addAttribute("studentGroupList", studentGroupList);
			model.addAttribute("courseGroupsList", courseGroupsList);
			model.addAttribute("userType", userType);
		} catch ( GroupsException e) {
			e.printStackTrace();
		}
		return "group";
	}
	
	@PostMapping("/group/{id}")
	public String updateGroupName(@PathVariable("id") long id, @ModelAttribute("group") GroupsDto group) {
		try {
			GroupsDto groupsDto = groupsService.get(id);
			groupsDto.setName(group.getName());
			
			groupsService.updateName(groupsDto);
			
		} catch (GroupsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:/showUserPage";
	}
	
	@PostMapping("/addStudentToGroup")
	public String addStudentToGroup(@ModelAttribute("student") StudentDto student, @ModelAttribute("group") GroupsDto group, Errors errors) {
		String[] s = student.getFirstName().split(" ");
		long idStudent = Long.valueOf(s[0]);
		try {
			StudentDto studentDtoTemp = studentService.get(idStudent);
			GroupsDto groupsDtoTemp = groupsService.get(group.getId());
			studentDtoTemp.setGroups(groupsDtoTemp);
			
			studentService.update(studentDtoTemp);
			
		} catch (StudentException | GroupsException e) {
			e.printStackTrace();
			errors.rejectValue("group", "", e.getMessage() + " Contact administrator");
			return "redirect:/stuff/group/" + group.getId();
		}
		return "redirect:/stuff/group/" + group.getId();
	}
	
	@PostMapping("/addCourseToGroup")
	public String addCourseToGroup(@ModelAttribute("group") GroupsDto group, Errors errors) {
		try {
			GroupsDto groupsDto = groupsService.get(group.getId());
			CourseDto courseDtoTemp = courseService.getByName(group.getCourse().get(0).getName());
			List<CourseDto> courseDtoList = groupsDto.getCourse();
			courseDtoList.add(courseDtoTemp);
			groupsDto.setCourse(courseDtoList);
			
			groupsService.update(groupsDto);
			
		} catch (CourseException | GroupsException  e) {
			e.printStackTrace();
			errors.rejectValue("group", "", e.getMessage() + " Contact administrator");
			return "redirect:/stuff/course/" + group.getId();
		}
		
		return "redirect:/stuff/group/" + group.getId();
	}
	
	@PostMapping("/deletStudentFromGroup")
	public String deleteStudentFromGroup(@ModelAttribute("student") StudentDto student, @ModelAttribute("group") GroupsDto group, Errors errors) {
		long idStudent = Long.valueOf(student.getFirstName());
		
		try {
			StudentDto studentTemp = studentService.get(idStudent);
			
			studentService.deletStudentFromGroup(studentTemp);
			
		} catch (StudentException  e) {
			e.printStackTrace();
		}
		return "redirect:/stuff/group/" + group.getId();
	}
	
	@GetMapping("/group/registration")
	public String groupRegistrationPage(@ModelAttribute("group") GroupsDto group, Model model) {
		model.addAttribute("userType", userType);
		return "registration/group_registration";
	}
	
	@PostMapping("/deletCourseFromGroup")
	public String deletCourseFromGroup(@ModelAttribute("group") GroupsDto group, Errors errors) {
		try {
			GroupsDto groupsDto = groupsService.get(group.getId());
			CourseDto courseDtoTemp = courseService.getByName(group.getCourse().get(0).getName());
			List<CourseDto> courseDtoList = groupsDto.getCourse();
			courseDtoList.remove(courseDtoTemp);
			groupsDto.setCourse(courseDtoList);
			
			groupsService.update(groupsDto);
			
		} catch (GroupsException | CourseException e) {
			e.printStackTrace();
			errors.rejectValue("group", "", e.getMessage() + " Contact administrator");
			return "redirect:/stuff/course/" + group.getId();
		}
		return "redirect:/stuff/group/" + group.getId();
	}
	
	@PostMapping("/group/registration")
	public String performGroupRegistration(@ModelAttribute("group") GroupsDto group, BindingResult bindingResult, Model model) {
		List<LocationDto> locationDtoList = locationService.getAll();
		model.addAttribute("locationDtoList", locationDtoList);
		groupsDtoValidator.validate(group, bindingResult);
		
		if (bindingResult.hasErrors())
			return "registration/group_registration";
		
		groupsService.add(group);
		
		return "redirect:/showUserPage";
	}
	
	@PostMapping("/deleteGroup")
	public String deletGroup(@ModelAttribute("group") GroupsDto group) {
		try {
			GroupsDto groupsTemp = groupsService.get(group.getId());
			groupsTemp.getStudent()
			.stream()
			.forEach(el->{
				try {
					studentService.deletStudentFromGroup(el);
				} catch (StudentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			groupsService.delete(group.getId());
		} catch (GroupsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "redirect:/showUserPage";
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
		long idTeacher = Long.valueOf(s[0]);
		try {
			TeacherDto teacherDtoTemp = teacherService.get(idTeacher);
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
		long idTeacher = Long.valueOf(teacher.getFirstName());
		try {
			TeacherDto teacherDtoTemp = teacherService.get(idTeacher);
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
