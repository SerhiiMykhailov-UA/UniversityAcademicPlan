package ua.foxminded.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.persistence.annotations.CascadeOnDelete;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.dto.StudentDto;
import ua.foxminded.entity.Student;
import ua.foxminded.exceptions.StudentException;
import ua.foxminded.mapper.CycleAvoidingMappingContext;
import ua.foxminded.mapper.StudentMapper;
import ua.foxminded.repository.StudentJPARepository;

@Service
@Transactional(readOnly = true)
@CascadeOnDelete
public class StudentService {
	
	private StudentMapper mapper;
	private StudentJPARepository repository;
	private final Logger logger = LogManager.getLogger();
	private CycleAvoidingMappingContext context = new CycleAvoidingMappingContext();

	public StudentService(StudentJPARepository repository, StudentMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}
	
	@Transactional(readOnly = false)
	public StudentDto add(StudentDto student) {
		logger.info("Add new student = {} in Grout", student);
		Student studentDao = mapper.studentDtoToStudent(student, context);
		Student studentResult = repository.saveAndFlush(studentDao);
		StudentDto studentDto = mapper.studentToStudentDto(studentResult, context);
		logger.info("OUT. Added new student = {}", studentDto);
		logger.info("------------------------------------------------");
		return studentDto;
	}
	
	public List<StudentDto> getAll() {
		logger.info("Get all students");
		List<StudentDto> students = repository.findAll()
				.stream().map(el -> mapper.studentToStudentDto(el, context)).collect(Collectors.toList());
		logger.info("OUT. Students list = {}", students);
		logger.info("------------------------------------------------");
		return students;
	}
	
	public StudentDto get(long id) throws StudentException {
		logger.info("Find student by id = {}", id);
		Student studentResult = repository.findById(id)
				.orElseThrow(() -> new StudentException("Cann't find student by id = " + id));
		StudentDto studentDto = mapper.studentToStudentDto(studentResult, context);
		logger.info("OUT student = {}", studentDto);
		logger.info("------------------------------------------------");
		return studentDto;
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
	public StudentDto update(StudentDto student) throws StudentException {
		logger.info("Update student = {} with courses", student);
		Student studentDao = mapper.studentDtoToStudent(student, context);
		Student studentTemp = repository.findById(studentDao.getId())
				.orElseThrow(() -> new StudentException("Cann't find student = " + studentDao));
		studentTemp.setCourse(studentDao.getCourse());
		studentTemp.setGroups(studentDao.getGroups());
		Student studentResult = repository.saveAndFlush(studentTemp);
		StudentDto studentDto = mapper.studentToStudentDto(studentResult, context);
		logger.info("OUT. Update student = {}", studentDto);
		logger.info("------------------------------------------------");
		return studentDto;
	}
	
	public boolean isExistByFirstNameAndLastName(String firstName, String lastName) {
		logger.info("Is exist by first name = {} and last name = {}", firstName, lastName);
		boolean studentResult = repository.existsByFirstNameAndLastName(firstName, lastName);
		logger.info("OUT : student exists {}", studentResult);
		logger.info("------------------------------------------------");
		return studentResult;
	}
	
	public StudentDto getByFirstNameAndLastName(String firstName, String lastName) throws StudentException {
		logger.info("Is student exist by first name = {} and last name = {}", firstName, lastName);
		Student studentResult = repository.findByFirstNameAndLastName(firstName, lastName)
				.orElseThrow(() -> new StudentException("Cann't find student = " + firstName + " " + lastName));
		StudentDto studentDto = mapper.studentToStudentDto(studentResult, context);
		logger.info("OUT : student {}", studentDto);
		logger.info("------------------------------------------------");
		return studentDto;
	}
}
