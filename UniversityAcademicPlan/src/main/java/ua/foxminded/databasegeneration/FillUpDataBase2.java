package ua.foxminded.databasegeneration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import ua.foxminded.dto.CourseDto;
import ua.foxminded.dto.TeacherDto;
import ua.foxminded.entity.Course;
import ua.foxminded.exceptions.CourseException;
import ua.foxminded.repository.CourseJPARepository;
import ua.foxminded.repository.TeacherJPARepository;
import ua.foxminded.service.CourseService;
import ua.foxminded.service.GroupsService;
import ua.foxminded.service.LectureService;
import ua.foxminded.service.LocationService;
import ua.foxminded.service.StudentService;
import ua.foxminded.service.TeacherService;

@Component
public class FillUpDataBase2 {
	private final CourseService courseService;
	private final StudentService studentService;
	private final GroupsService groupService;
	private final TeacherService teacherService;
	private final LocationService locationService;
	private final LectureService lectureService;
	private final CourseJPARepository courseJPARepository;
	private final TeacherJPARepository teacherJPARepository;
	
	private final Logger logger = LogManager.getLogger();


	private FillUpDataBase2(CourseService courseService, StudentService studentService,
			GroupsService groupService, TeacherService teacherService, LocationService locationService, LectureService lectureService, TeacherJPARepository teacherJPARepository, CourseJPARepository courseJPARepository) {
		this.courseService = courseService;
		this.studentService = studentService;
		this.groupService = groupService; 
		this.teacherService = teacherService;
		this.locationService = locationService;
		this.lectureService = lectureService;
		this.courseJPARepository = courseJPARepository;
		this.teacherJPARepository = teacherJPARepository;
	}

	public void dataBaseConfiguration() {

		// Assign teachers to courses
		int i = 0;
		List<CourseDto> courseDtos = courseService.getAll();
		List<TeacherDto> teachers = teacherService.getAll();
		logger.info("Teacher id=8 {}", teachers.get(7));
		logger.info("Teacher id=9 {}", teachers.get(8));
		for(CourseDto c : courseDtos) {
			if (i == 9) {
				i = 0;
			}
			c.setTeacher(new ArrayList<>(Arrays.asList(teachers.get(i))));
			logger.info("teacher list = {}", c.getTeacher());
			try {
				courseService.addOrDeleteTeacherToCourse(c);
				Course course = courseJPARepository.findById(c.getId()).orElseThrow(()-> new CourseException(""));
			} catch (CourseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
	}
}
