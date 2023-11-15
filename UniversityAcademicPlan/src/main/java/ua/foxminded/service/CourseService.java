package ua.foxminded.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.dto.CourseDto;
import ua.foxminded.entity.Course;
import ua.foxminded.exceptions.CourseException;
import ua.foxminded.mapper.CourseMapper;
import ua.foxminded.mapper.CycleAvoidingMappingContext;
import ua.foxminded.repository.CourseJPARepository;

@Service
@Transactional(readOnly = true)
public class CourseService {

	private CourseMapper mapper;
	private CourseJPARepository courseJPARepository;
	private final Logger logger = LogManager.getLogger();
	private final CycleAvoidingMappingContext context;
	
	public CourseService(CourseJPARepository courseJPARepository, CourseMapper mapper, CycleAvoidingMappingContext context) {
		this.courseJPARepository = courseJPARepository;
		this.mapper = mapper;
		this.context = context;
	}
	
	public CourseDto get(long id) throws CourseException {
		logger.info("Get course by id = {}", id);
		Course courseResult = courseJPARepository.findById(id)
				.orElseThrow(()-> new CourseException("Cann't find by id = " + id));
		CourseDto courseDto = mapper.courseToCourseDto(courseResult, context);
		logger.info("OUT: result get course = {}", courseDto);
		return courseDto;
	}
	
	public CourseDto getByName(String name) throws CourseException {
		logger.info("Get course by name = {}", name);
		Course courseResult = courseJPARepository.findByName(name)
				.orElseThrow(()-> new CourseException("Cann't find by name = " + name));
		CourseDto courseDto = mapper.courseToCourseDto(courseResult, context);
		logger.info("OUT: result geting course = {}", courseDto);
		return courseDto;
	}
	
	public List<CourseDto> getAll() {
		logger.info("Get all courses");
		List<Course> courseDao = courseJPARepository.findAll();
		List<CourseDto> courses = courseDao
				.stream().map(el -> mapper.courseToCourseDto(el, context)).collect(Collectors.toList());
		logger.info("OUT: result get all courses = {}", courses);
		return courses;
	}
	
	@Transactional(readOnly = false)
	public CourseDto add(CourseDto course) {
		logger.info("Add new group = {}", course);
		Course courseDao = mapper.courseDtoToCourse(course, context);
		Course courseResult = courseJPARepository.saveAndFlush(courseDao);
		CourseDto courseDto = mapper.courseToCourseDto(courseResult, context);
		logger.info("OUT result group = {}", courseDto);
		return courseDto;
	}
	
	@Transactional(readOnly = false)
	public boolean delete(long id) {
		logger.info("Delete location by id = {}", id);
		if (courseJPARepository.existsById(id)) {
			courseJPARepository.deleteById(id);
			logger.info("Deleting result = {}", true);
			return true;
		} else {
			logger.info("Deleting result = {}", false);
			return false;
		}
	}
	
	@Transactional(readOnly = false)
	public CourseDto update(CourseDto course) throws CourseException {
		logger.info("Update course = {}", course);
		Course courseDao = mapper.courseDtoToCourse(course, context);
		Course courseTemp = courseJPARepository.findByName(courseDao.getName())
				.orElseThrow(()-> new CourseException("Cann't find group by name = " + course.getName()));
		courseTemp.setGroups(courseDao.getGroups());
		courseTemp.setLecture(courseDao.getLecture());
		courseTemp.setLocation(courseDao.getLocation());
		courseTemp.setSchedule(courseDao.getSchedule());
		courseTemp.setStudent(courseDao.getStudent());
		courseTemp.setTeacher(courseDao.getTeacher());
		Course courseResult = courseJPARepository.saveAndFlush(courseTemp);
		CourseDto courseDto = mapper.courseToCourseDto(courseResult, context);
		logger.info("OUT result course = {}", courseDto);
		return courseDto;
	}
	
	public boolean ifExistsByName(String name) {
		logger.info("Find group by name = {}", name);
		boolean groupResult = courseJPARepository.existsByName(name);
		logger.info("OUT: result finding group = {}", groupResult);
		return groupResult;
	}
}
