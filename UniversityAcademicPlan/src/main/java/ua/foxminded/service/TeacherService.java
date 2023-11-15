package ua.foxminded.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.dto.TeacherDto;
import ua.foxminded.entity.Teacher;
import ua.foxminded.exceptions.TeacherException;
import ua.foxminded.mapper.CycleAvoidingMappingContext;
import ua.foxminded.mapper.TeacherMapper;
import ua.foxminded.repository.TeacherJPARepository;

@Service
@Transactional(readOnly = true)
public class TeacherService {

	private TeacherMapper mapper;
	private TeacherJPARepository repository;
	private final Logger logger = LogManager.getLogger();
	private CycleAvoidingMappingContext context = new CycleAvoidingMappingContext();

	public TeacherService(TeacherJPARepository repository, TeacherMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Transactional(readOnly = false)
	public TeacherDto add(TeacherDto teacher) {
		logger.info("Add new teacher = {} with courses taught", teacher);
		Teacher teacherDao = mapper.teacherDtoToTeacher(teacher, context);
		Teacher teacherResult = repository.saveAndFlush(teacherDao);
		TeacherDto teacherDto = mapper.teacherToTeacherDto(teacherResult, context);
		logger.info("OUT. Added new teacher = {}", teacherDto);
		logger.info("------------------------------------------------");
		return teacherDto;
	}

	public List<TeacherDto> getAll() {
		logger.info("Get all teacheres");
		List<TeacherDto> teachers = repository.findAll()
				.stream().map(el -> mapper.teacherToTeacherDto(el, context)).collect(Collectors.toList());
		logger.info("OUT teachers list = {}", teachers);
		logger.info("------------------------------------------------");
		return teachers;
	}

	public TeacherDto get(long id) throws TeacherException {
		logger.info("Find teacher by id = {}", id);
		Teacher teacherResult = repository.findById(id)
				.orElseThrow(() -> new TeacherException("Cann't find teacher by id = " + id));
		TeacherDto teacherDto = mapper.teacherToTeacherDto(teacherResult, context);
		logger.info("OUT teacher = {}", teacherDto);
		logger.info("------------------------------------------------");
		return teacherDto;
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
	public TeacherDto update(TeacherDto teacher) throws TeacherException {
		logger.info("Update teacher = {} with courses taught", teacher);
		Teacher teacherDao = mapper.teacherDtoToTeacher(teacher, context);
		Teacher teacherTemp = repository.findById(teacherDao.getId())
				.orElseThrow(() -> new TeacherException("Cann't find teacher = " + teacher));
		teacherTemp.setCourses(teacherDao.getCourses());
		Teacher teacherResult = repository.saveAndFlush(teacherTemp);
		TeacherDto teacherDto = mapper.teacherToTeacherDto(teacherResult, context);
		logger.info("OUT. Update teacher = {}", teacherDto);
		logger.info("------------------------------------------------");
		return teacherDto;
	}

	public boolean isExistByFirstNameAndLastName(String firstName, String lastName) {
		logger.info("Is exist by first name = {} and last name = {}", firstName, lastName);
		boolean teacherResult = repository.existsByFirstNameAndLastName(firstName, lastName);
		logger.info("OUT : {}", teacherResult);
		logger.info("------------------------------------------------");
		return teacherResult;
	}

	public TeacherDto getByFirstNameAndLastName(String firstName, String lastName) throws TeacherException {
		logger.info("Is exist by first name = {} and last name = {}", firstName, lastName);
		Teacher teacherResult = repository.findByFirstNameAndLastName(firstName, lastName)
				.orElseThrow(() -> new TeacherException("Cann't find teacher = " + firstName + " " + lastName));
		TeacherDto teacherDto = mapper.teacherToTeacherDto(teacherResult, context);
		logger.info("OUT : {}", teacherDto);
		logger.info("------------------------------------------------");
		return teacherDto;
	}

}
