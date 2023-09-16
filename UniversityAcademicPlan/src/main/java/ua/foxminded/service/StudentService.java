package ua.foxminded.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.persistence.annotations.CascadeOnDelete;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.entity.Student;
import ua.foxminded.exceptions.StudentException;
import ua.foxminded.repository.StudentJPARepository;

@Service
@Transactional(readOnly = true)
@CascadeOnDelete
public class StudentService {
	
	private StudentJPARepository repository;
	private final Logger logger = LogManager.getLogger();

	public StudentService(StudentJPARepository repository) {
		this.repository = repository;
	}
	
	@Transactional(readOnly = false)
	public Student add(Student student) {
		logger.info("Add new student = {} in Grout", student);
		Student studentResult = repository.saveAndFlush(student);
		logger.info("OUT. Added new student = {}", studentResult);
		logger.info("------------------------------------------------");
		return studentResult;
	}
	
	public List<Student> getAll() {
		logger.info("Get all students");
		List<Student> students = repository.findAll();
		logger.info("OUT. Students list = {}", students);
		logger.info("------------------------------------------------");
		return students;
	}
	
	public Student get(long id) throws StudentException {
		logger.info("Find student by id = {}", id);
		Student studentResult = repository.findById(id)
				.orElseThrow(() -> new StudentException("Cann't find student by id = " + id));
		logger.info("OUT student = {}", studentResult);
		logger.info("------------------------------------------------");
		return studentResult;
	}
	
	@Transactional(readOnly = false)
	public boolean delete(long id) {
		logger.info("Delete student by id = ", id);
		if (repository.existsById(id)) {
			repository.deleteById(id);
			logger.info("Delete student by = ", true);
			logger.info("------------------------------------------------");
			return true;
		} else {
			logger.info("Delete student by = {}. Student isn't exists", true);
			logger.info("------------------------------------------------");
			return false;
		}
	}
	
	@Transactional(readOnly = false)
	public Student update(Student student) throws StudentException {
		logger.info("Update student = {} with courses", student);
		Student studentTemp = repository.findById(student.getId())
				.orElseThrow(() -> new StudentException("Cann't find student = " + student));
		studentTemp.setCourse(student.getCourse());
		studentTemp.setGroups(student.getGroups());
		Student studentResult = repository.saveAndFlush(studentTemp);
		logger.info("OUT. Update student = {}", studentResult);
		logger.info("------------------------------------------------");
		return studentResult;
	}
	
	public boolean isExistByFirstNameAndLastName(String firstName, String lastName) {
		logger.info("Is exist by first name = {} and last name = {}", firstName, lastName);
		boolean studentResult = repository.existsByFirstNameAndLastName(firstName, lastName);
		logger.info("OUT : student exists {}", studentResult);
		logger.info("------------------------------------------------");
		return studentResult;
	}
	
	public Student getByFirstNameAndLastName(String firstName, String lastName) throws StudentException {
		logger.info("Is student exist by first name = {} and last name = {}", firstName, lastName);
		Student studentResult = repository.findByFirstNameAndLastName(firstName, lastName)
				.orElseThrow(() -> new StudentException("Cann't find student = " + firstName + " " + lastName));
		logger.info("OUT : student {}", studentResult);
		logger.info("------------------------------------------------");
		return studentResult;
	}
}
