package ua.foxminded.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.entity.Course;
import ua.foxminded.exceptions.CourseException;
import ua.foxminded.repository.CourseJPARepository;

@Service
@Transactional(readOnly = true)
public class CourseService {

	private final CourseJPARepository repository;
	private final Logger logger = LogManager.getLogger();
	
	public CourseService(CourseJPARepository repository) {
		this.repository = repository;
	}
	
	public Course get(long id) throws CourseException {
		logger.info("Get course by id = {}", id);
		Course courseResult = repository.findById(id)
				.orElseThrow(()-> new CourseException("Cann't find by id = " + id));
		logger.info("OUT: result get course = {}", courseResult);
		return courseResult;
	}
	
	public Course getByName(String name) throws CourseException {
		logger.info("Get course by name = {}", name);
		Course courseResult = repository.findByName(name)
				.orElseThrow(()-> new CourseException("Cann't find by name = " + name));
		logger.info("OUT: result geting course = {}", courseResult);
		return courseResult;
	}
	
	public List<Course> getAll() {
		logger.info("Get all courses");
		List<Course> courses = repository.findAll();
		logger.info("OUT: result get all courses = {}", courses);
		return courses;
	}
	
	@Transactional(readOnly = false)
	public Course add(Course course) {
		logger.info("Add new group = {}", course);
		Course courseResult = repository.saveAndFlush(course);
		logger.info("OUT result group = {}", courseResult);
		return courseResult;
	}
	
	@Transactional(readOnly = false)
	public boolean delete(long id) {
		logger.info("Delete location by id = {}", id);
		if (repository.existsById(id)) {
			repository.deleteById(id);
			logger.info("Deleting result = {}", true);
			return true;
		} else {
			logger.info("Deleting result = {}", false);
			return false;
		}
	}
	
	@Transactional(readOnly = false)
	public Course update(Course course) throws CourseException {
		logger.info("Update course = {}", course);
		Course courseTemp = repository.findByName(course.getName())
				.orElseThrow(()-> new CourseException("Cann't find group by name = " + course.getName()));
		courseTemp.setGroups(course.getGroups());
		courseTemp.setLecture(course.getLecture());
		courseTemp.setLocation(course.getLocation());
		courseTemp.setSchedule(course.getSchedule());
		courseTemp.setStudent(course.getStudent());
		courseTemp.setTeacher(course.getTeacher());
		Course courseResult = repository.saveAndFlush(courseTemp);
		logger.info("OUT result course = {}", courseResult);
		return courseResult;
	}
	
	public boolean ifExistsByName(String name) {
		logger.info("Find group by name = {}", name);
		boolean groupResult = repository.existsByName(name);
		logger.info("OUT: result finding group = {}", groupResult);
		return groupResult;
	}
}
