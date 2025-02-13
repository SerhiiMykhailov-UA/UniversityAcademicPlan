package ua.foxminded.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.dto.CourseDto;
import ua.foxminded.entity.Course;
import ua.foxminded.entity.Location;
import ua.foxminded.exceptions.CourseException;
import ua.foxminded.exceptions.LocationException;
import ua.foxminded.mapper.CourseMapper;
import ua.foxminded.mapper.CycleAvoidingMappingContext;
import ua.foxminded.repository.CourseJPARepository;
import ua.foxminded.repository.LocationJPARepository;

@Service
@Transactional(readOnly = true)
public class CourseService {

	private CourseMapper mapper;
	private final CourseJPARepository courseJPARepository;
	private final LocationJPARepository locationJPARepository;
	private final Logger logger = LogManager.getLogger();
	private CycleAvoidingMappingContext context = new CycleAvoidingMappingContext();
	
	public CourseService(CourseJPARepository courseJPARepository, CourseMapper mapper,
			LocationJPARepository locationJPARepository, LocationService locationService) {
		this.courseJPARepository = courseJPARepository;
		this.mapper = mapper;
		this.locationJPARepository = locationJPARepository;
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
		List<CourseDto> courseDtos = courseDao
				.stream()
				.map(el -> mapper.courseToCourseDto(el, context))
				.collect(Collectors.toList());
		logger.info("OUT: result get all courses = {}", courseDtos);
		return courseDtos;
	}
	
	@Transactional(readOnly = false)
	public CourseDto add(CourseDto course) throws LocationException {
		logger.info("Add new course = {}", course);
		Course courseDao = mapper.courseDtoToCourse(course, context);
		Location location = locationJPARepository.findByName(courseDao.getLocation().getName())
				.orElseThrow(()-> new LocationException("Location isn't exist"));
		courseDao.setLocation(location);
		Course courseResult = courseJPARepository.saveAndFlush(courseDao);
		CourseDto courseDto = mapper.courseToCourseDto(courseResult, context);
		logger.info("OUT result course = {}", courseDto);
		return courseDto;
	}
	
	@Transactional(readOnly = false)
	public boolean delete(long id) {
		logger.info("Delete Course by id = {}", id);
		if (courseJPARepository.existsById(id)) {
			courseJPARepository.deleteById(id);
			logger.info("Deleting result = {}", true);
			return true;
		} else {
			logger.info("Deleting result course = {}", false);
			return false;
		}
	}
	
	@Transactional(readOnly = false)
	public CourseDto update(CourseDto course) throws CourseException, LocationException {
		logger.info("Update course = {} teacher = {}", course, course.getTeacher());
		Location location = locationJPARepository.findByName(course.getLocation().getName())
				.orElseThrow(()->new LocationException("Location isn't exist"));
		Course courseDao = mapper.courseDtoToCourse(course, context);
		logger.info("Update teacherDao = {}", courseDao.getTeacher());
		Course courseTemp = courseJPARepository.findById(courseDao.getId())
				.orElseThrow(()-> new CourseException("Cann't find group by name = " + course.getName()));
		logger.info("Update teacherTemp = {}", courseTemp.getTeacher());
		if (course.getName() != null && !courseTemp.getName().equals(courseDao.getName())) 
			courseTemp.setName(courseDao.getName());
		if (course.getGroups() != null && !courseTemp.getGroups().equals(courseDao.getGroups()))
			courseTemp.setGroups(courseDao.getGroups());
		if (course.getLecture() != null && !courseTemp.getLecture().equals(courseDao.getLecture()))
			courseTemp.setLecture(courseDao.getLecture());
		if (course.getLocation() != null && !courseTemp.getLocation().getName().equals(courseDao.getLocation().getName()))
			courseTemp.setLocation(location);
		if (course.getSchedule() != null && !courseTemp.getSchedule().equals(courseDao.getSchedule()))
			courseTemp.setSchedule(courseDao.getSchedule());
		if (course.getTeacher() != null )
			courseTemp.setTeacher(courseDao.getTeacher());
		if (course.getStudent() != null && !courseTemp.getStudent().equals(courseDao.getStudent()))
			courseTemp.setStudent(courseDao.getStudent());
		Course courseResult = courseJPARepository.save(courseTemp);
		CourseDto courseDto = mapper.courseToCourseDto(courseResult, context);
		logger.info("OUT result course = {} teacher = {}", courseDto, courseDto.getTeacher());
		return courseDto;
	}
	
	@Transactional(readOnly = false)
	public CourseDto addOrDeleteTeacherToCourse (CourseDto course) throws CourseException {
		logger.info("Update course = {} teacher = {}", course, course.getTeacher());
		Course courseDao = mapper.courseDtoToCourse(course, context);
		logger.info("Update teacherDao = {}", courseDao.getTeacher());
		Course courseTemp = courseJPARepository.findById(courseDao.getId())
				.orElseThrow(()-> new CourseException("Cann't find group by name = " + course.getName()));
		logger.info("Update teacherTemp = {}", courseTemp.getTeacher());
		courseTemp.setTeacher(courseDao.getTeacher());
		
		Course courseResult = courseJPARepository.save(courseTemp);
		
		CourseDto courseDto = mapper.courseToCourseDto(courseResult, context);
		logger.info("OUT result course = {} teacher = {}", courseDto, courseDto.getTeacher());
		return courseDto;
	}
	
	public boolean ifExistsByName(String name) {
		logger.info("Find course by name = {}", name);
		boolean groupResult = courseJPARepository.existsByName(name);
		logger.info("OUT: result finding course = {}", groupResult);
		return groupResult;
	}
}
