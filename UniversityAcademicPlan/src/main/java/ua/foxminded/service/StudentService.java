package ua.foxminded.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class StudentService {
	
	private final StudentMapper mapper;
	private final StudentJPARepository repository;
	private PasswordEncoder passwordEncoder;
	private final Logger logger = LogManager.getLogger();
	private CycleAvoidingMappingContext context = new CycleAvoidingMappingContext();

	public StudentService(StudentJPARepository repository, StudentMapper mapper, PasswordEncoder passwordEncoder) {
		this.repository = repository;
		this.mapper = mapper;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Transactional(readOnly = false)
	public StudentDto add(StudentDto student) {
		logger.info("Add new student = {} in Group", student);
		Student studentDao = mapper.studentDtoToStudent(student, context);
		studentDao.setPassword(passwordEncoder.encode(student.getPassword()));
		Student studentResult = repository.saveAndFlush(studentDao);
		StudentDto studentDto = mapper.studentToStudentDto(studentResult, context);
		logger.info("OUT. Added new student = {}", studentDto);
		logger.info("------------------------------------------------");
		return studentDto;
	}
	
	public List<StudentDto> getAll() {
		logger.info("Get all students");
		List<StudentDto> students = repository.findAll()
				.stream()
				.map(el -> mapper.studentToStudentDto(el, context))
				.collect(Collectors.toList());
		logger.info("OUT. Students list = {}", students);
		logger.info("------------------------------------------------");
		return students;
	}
	
	public StudentDto get(long id) throws StudentException {
		logger.info("Get student by id = {}", id);
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
			logger.info("Delete student by = {}. Student isn't exist", false);
			logger.info("------------------------------------------------");
			return false;
		}
	}
	
	@Transactional(readOnly = false)
	public StudentDto update(StudentDto student) throws StudentException {
		logger.info("Update student = {}", student);
		Student studentDao = mapper.studentDtoToStudent(student, context);
		Student studentTemp = repository.findById(studentDao.getId())
				.orElseThrow(() -> new StudentException("Cann't find student = " + studentDao));
		if (student.getFirstName() != null && !studentTemp.getFirstName().equals(studentDao.getFirstName()))
			studentTemp.setFirstName(studentDao.getFirstName());
		if (student.getLastName() != null && !studentTemp.getLastName().equals(studentDao.getLastName()))
			studentTemp.setLastName(studentDao.getLastName());
		if (student.getCourse() != null && !studentTemp.getCourse().equals(studentDao.getCourse()))
			studentTemp.setCourse(studentDao.getCourse());
		if (student.getGroups() != null)
			studentTemp.setGroups(studentDao.getGroups());
		if (student.getPassword() != null && !studentTemp.getPassword().equals(studentDao.getPassword()))
			studentTemp.setPassword(passwordEncoder.encode(studentDao.getPassword()));
		Student studentResult = repository.saveAndFlush(studentTemp);
		StudentDto studentDto = mapper.studentToStudentDto(studentResult, context);
		logger.info("OUT. Update student = {}", studentDto);
		logger.info("------------------------------------------------");
		return studentDto;
	}
	
	@Transactional(readOnly = false)
	public StudentDto deletStudentFromGroup(StudentDto student) throws StudentException {
		logger.info("Delete student = {} from group ", student);
		Student studentTemp = repository.findById(student.getId())
				.orElseThrow(() -> new StudentException("Cann't find student = " + student));
			studentTemp.setGroups(null);
		Student studentResult = repository.saveAndFlush(studentTemp);
		StudentDto studentDto = mapper.studentToStudentDto(studentResult, context);
		logger.info("OUT. delete student = {} from group", studentDto);
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
	
	public boolean isExistByid(Long id) {
		logger.info("Is admin exist by id = {}", id);
		boolean isExistById = repository.existsById(id);
		logger.info("OUT : admin exists = {}", isExistById);
		logger.info("------------------------------------------------");
		return isExistById;
	}
	
	public StudentDto getByFirstNameAndLastName(String firstName, String lastName) throws StudentException {
		logger.info("Get student by first name = {} and last name = {}", firstName, lastName);
		Student studentResult = repository.findByFirstNameAndLastName(firstName, lastName)
				.orElseThrow(() -> new StudentException("Cann't find student = " + firstName + " " + lastName));
		StudentDto studentDto = mapper.studentToStudentDto(studentResult, context);
		logger.info("OUT : student {}", studentDto);
		logger.info("------------------------------------------------");
		return studentDto;
	}
}
