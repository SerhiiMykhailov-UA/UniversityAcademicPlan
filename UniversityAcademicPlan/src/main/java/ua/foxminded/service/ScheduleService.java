package ua.foxminded.service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.entity.Schedule;
import ua.foxminded.exceptions.ScheduleException;
import ua.foxminded.repository.ScheduleJPARepository;

@Service
@Transactional(readOnly = true)
public class ScheduleService {
	private ScheduleJPARepository repository;
	private final Logger logger = LogManager.getLogger();

	public ScheduleService(ScheduleJPARepository repository) {
		this.repository = repository;
	}

	public Schedule get(long id) throws ScheduleException {
		logger.info("Get schedule id = {}", id);
		Schedule scheduleResult = repository.findById(id)
				.orElseThrow(() -> new ScheduleException("Cann't find schdedule by id = " + id));
		logger.info("OUT result schedule = {}", scheduleResult);
		return scheduleResult;
	}

	public List<Schedule> getAll() {
		logger.info("Get all schedule");
		List<Schedule> scheduleList = repository.findAll();
		logger.info("OUT. Schedule list = {}", scheduleList);
		logger.info("------------------------------------------------");
		return scheduleList;
	}

	@Transactional(readOnly = false)
	public Schedule add(Schedule schedule) {
		logger.info("Add new schedule = {}", schedule);
		Schedule scheduleResult = repository.saveAndFlush(schedule);
		logger.info("OUT result shedule = {}", scheduleResult);
		return scheduleResult;
	}

	@Transactional(readOnly = false)
	public boolean delete(long id) {
		logger.info("Delete schedule by id = {}", id);
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
	public Schedule update(Schedule schedule) throws ScheduleException {
		logger.info("Update schedule = {}", schedule);
		Schedule scheduleTemp = repository.findByStartTimeAndEndTimeAndDayOfWeek(schedule.getStartTime(), schedule.getEndTime(), schedule.getDayOfWeek())
				.orElseThrow(()-> new ScheduleException("Cann't find schdedule by id = " + schedule.getId()));
		scheduleTemp.setCourse(schedule.getCourse());
		Schedule scheduleResult = repository.saveAndFlush(scheduleTemp);
		logger.info("OUT result shedule = {}", scheduleResult);
		return scheduleResult;
	}
	
	public boolean existsByStartTimeAndEndTimeAndDayOfWeek(LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek) {
		logger.info("Find schedule by StartTime = {}, EndTime = {}, DayOfWeek = {}", startTime, endTime, dayOfWeek);
		boolean scheduleResult = repository.existsByStartTimeAndEndTimeAndDayOfWeek(startTime, endTime, dayOfWeek);
		logger.info("OUT: result finding schedule boolean = {}", scheduleResult);
		return scheduleResult;
	}
	
	public Schedule getByStartTimeAndEndTimeAndDayOfWeek(LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek) throws ScheduleException {
		logger.info("Get schedule by StartTime = {}, EndTime = {}, DayOfWeek = {}", startTime, endTime, dayOfWeek);
		Schedule scheduleResult = repository.findByStartTimeAndEndTimeAndDayOfWeek(startTime, endTime, dayOfWeek)
				.orElseThrow(()-> new ScheduleException("Cann't find schdedule by StartTime = " + startTime + "EndTime = " + endTime + "DayOfWeek = " + dayOfWeek));
		logger.info("OUT: result geting schedule = {}", scheduleResult);
		return scheduleResult;
	}
}
