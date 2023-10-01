package ua.foxminded.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.entity.Teacher;
import ua.foxminded.exceptions.TeacherException;
import ua.foxminded.repository.TeacherJPARepository;

@Service
@Transactional(readOnly = true)
public class TeacherService {

	private TeacherJPARepository repository;
	private final Logger logger = LogManager.getLogger();

	public TeacherService(TeacherJPARepository repository) {
		this.repository = repository;
	}

	@Transactional(readOnly = false)
	public Teacher add(Teacher teacher) {
		logger.info("Add new teacher = {} with courses taught", teacher);
		Teacher teacherResult = repository.saveAndFlush(teacher);
		logger.info("OUT. Added new teacher = {}", teacherResult);
		logger.info("------------------------------------------------");
		return teacherResult;
	}

	public List<Teacher> getAll() {
		logger.info("Get all teacheres");
		List<Teacher> teachers = repository.findAll();
		logger.info("OUT teachers list = {}", teachers);
		logger.info("------------------------------------------------");
		return teachers;
	}

	public Teacher get(long id) throws TeacherException {
		logger.info("Find teacher by id = {}", id);
		Teacher teacherResult = repository.findById(id)
				.orElseThrow(() -> new TeacherException("Cann't find teacher by id = " + id));
		logger.info("OUT teacher = {}", teacherResult);
		logger.info("------------------------------------------------");
		return teacherResult;
	}

	@Transactional(readOnly = false)
	public boolean delete(long id) {
		logger.info("Delete teacher by id = ", id);
		if (repository.existsById(id)) {
			repository.deleteById(id);
			logger.info("Delete teacher by = ", true);
			logger.info("------------------------------------------------");
			return true;
		} else {
			logger.info("Delete teacher by = {}. Teacher isn't exists", true);
			logger.info("------------------------------------------------");
			return false;
		}
	}

	@Transactional(readOnly = false)
	public Teacher update(Teacher teacher) throws TeacherException {
		logger.info("Update teacher = {} with courses taught", teacher);
		Teacher teacherTemp = repository.findById(teacher.getId())
				.orElseThrow(() -> new TeacherException("Cann't find teacher = " + teacher));
		teacherTemp.setCourses(teacher.getCourses());
		Teacher teacherResult = repository.saveAndFlush(teacherTemp);
		logger.info("OUT. Update teacher = {}", teacherResult);
		logger.info("------------------------------------------------");
		return teacherResult;
	}

	public boolean isExistByFirstNameAndLastName(String firstName, String lastName) {
		logger.info("Is exist by first name = {} and last name = {}", firstName, lastName);
		boolean teacherResult = repository.existsByFirstNameAndLastName(firstName, lastName);
		logger.info("OUT : {}", teacherResult);
		logger.info("------------------------------------------------");
		return teacherResult;
	}

	public Teacher getByFirstNameAndLastName(String firstName, String lastName) throws TeacherException {
		logger.info("Is exist by first name = {} and last name = {}", firstName, lastName);
		Teacher teacherResult = repository.findByFirstNameAndLastName(firstName, lastName)
				.orElseThrow(() -> new TeacherException("Cann't find teacher = " + firstName + " " + lastName));
		logger.info("OUT : {}", teacherResult);
		logger.info("------------------------------------------------");
		return teacherResult;
	}

}
