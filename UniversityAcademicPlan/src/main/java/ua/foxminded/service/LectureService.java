package ua.foxminded.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.entity.Lecture;
import ua.foxminded.exceptions.LectureException;
import ua.foxminded.repository.LectureJPARepository;

@Service
@Transactional(readOnly = true)
public class LectureService {

	private final LectureJPARepository repository;
	private final Logger logger = LogManager.getLogger();

	public LectureService(LectureJPARepository repository) {
		this.repository = repository;
	}
	
	public Lecture get(long id) throws LectureException {
		logger.info("Get by id = {}", id);
		Lecture lectureResult = repository.findById(id)
				.orElseThrow(()-> new LectureException("Cann't find lecture by id = " + id));
		logger.info("OUT: get result Lecture = {}", lectureResult);
		return lectureResult;
	}
	
	public Lecture getByName(String name) throws LectureException {
		logger.info("Get by name = {}", name);
		Lecture lectureResult = repository.findByName(name)
				.orElseThrow(()-> new LectureException("Cann't find lecture by name = " + name));
		logger.info("OUT: get result Lecture = {}", lectureResult);
		return lectureResult;
	}
	
	public List<Lecture> getAll() {
		logger.info("Get all");
		List<Lecture> lectures = repository.findAll();
		logger.info("OUT: result get all lectures = {}", lectures);
		return lectures;
	}
	
	@Transactional(readOnly = false)
	public Lecture add(Lecture lecture) {
		logger.info("Add new lecture = {}", lecture);
		Lecture locationResult = repository.saveAndFlush(lecture);
		logger.info("OUT result lecture = {}", locationResult);
		return locationResult;
	}
	
	@Transactional(readOnly = false)
	public boolean delete(long id) {
		logger.info("Delete lecture by id = {}", id);
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
	public Lecture update(Lecture lecture) throws LectureException {
		logger.info("Update lecture = {}", lecture);
		Lecture lectureTemp = repository.findByName(lecture.getName())
				.orElseThrow(()-> new LectureException("Cann't find lecture by name = " + lecture.getName()));
		lectureTemp.setCourse(lecture.getCourse());
		Lecture lectureResult = repository.saveAndFlush(lectureTemp);
		logger.info("OUT result lecture = {}", lectureResult);
		return lectureResult;
	}
	
	public boolean ifExistsByName(String name) {
		logger.info("Find lecture by name = {}", name);
		boolean lectureResult = repository.existsByName(name);
		logger.info("OUT: result finding lecture = {}", lectureResult);
		return lectureResult;
	}
}
