package ua.foxminded.service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.dto.ScheduleDto;
import ua.foxminded.entity.Schedule;
import ua.foxminded.exceptions.ScheduleException;
import ua.foxminded.mapper.CycleAvoidingMappingContext;
import ua.foxminded.mapper.ScheduleMapper;
import ua.foxminded.repository.ScheduleJPARepository;

@Service
@Transactional(readOnly = true)
public class ScheduleService {
	
	private ScheduleMapper mapper;
	private ScheduleJPARepository repository;
	private final Logger logger = LogManager.getLogger();
	private CycleAvoidingMappingContext context = new CycleAvoidingMappingContext();

	public ScheduleService(ScheduleJPARepository repository, ScheduleMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	public ScheduleDto get(long id) throws ScheduleException {
		logger.info("Get schedule id = {}", id);
		Schedule scheduleResult = repository.findById(id)
				.orElseThrow(() -> new ScheduleException("Cann't find schdedule by id = " + id));
		ScheduleDto scheduleDto = mapper.scheduleToScheduleDto(scheduleResult, context);
		logger.info("OUT result schedule = {}", scheduleDto);
		return scheduleDto;
	}

	public List<ScheduleDto> getAll() {
		logger.info("Get all schedule");
		List<ScheduleDto> scheduleList = repository.findAll()
				.stream().map(el -> mapper.scheduleToScheduleDto(el, context)).collect(Collectors.toList());
		logger.info("OUT. Schedule list = {}", scheduleList);
		logger.info("------------------------------------------------");
		return scheduleList;
	}

	@Transactional(readOnly = false)
	public ScheduleDto add(ScheduleDto schedule) {
		logger.info("Add new schedule = {}", schedule);
		Schedule scheduleDao = mapper.scheduleDtoToSchedule(schedule, context);
		Schedule scheduleResult = repository.saveAndFlush(scheduleDao);
		ScheduleDto scheduleDto = mapper.scheduleToScheduleDto(scheduleResult, context);
		logger.info("OUT result shedule = {}", scheduleDto);
		return scheduleDto;
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
	public ScheduleDto update(ScheduleDto schedule) throws ScheduleException {
		logger.info("Update schedule = {}", schedule);
		Schedule scheduleDao = mapper.scheduleDtoToSchedule(schedule, context);
		Schedule scheduleTemp = repository.findByStartTimeAndEndTimeAndDayOfWeek(scheduleDao.getStartTime(), scheduleDao.getEndTime(), scheduleDao.getDayOfWeek())
				.orElseThrow(()-> new ScheduleException("Cann't find schdedule by id = " + scheduleDao.getId()));
		if (!scheduleTemp.getCourse().equals(scheduleDao.getCourse()))
			scheduleTemp.setCourse(scheduleDao.getCourse());
		Schedule scheduleResult = repository.saveAndFlush(scheduleTemp);
		ScheduleDto scheduleDto = mapper.scheduleToScheduleDto(scheduleResult, context);
		logger.info("OUT result shedule = {}", scheduleDto);
		return scheduleDto;
	}
	
	public boolean existsByStartTimeAndEndTimeAndDayOfWeek(LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek) {
		logger.info("Find schedule by StartTime = {}, EndTime = {}, DayOfWeek = {}", startTime, endTime, dayOfWeek);
		boolean scheduleResult = repository.existsByStartTimeAndEndTimeAndDayOfWeek(startTime, endTime, dayOfWeek);
		logger.info("OUT: result finding schedule boolean = {}", scheduleResult);
		return scheduleResult;
	}
	
	public ScheduleDto getByStartTimeAndEndTimeAndDayOfWeek(LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek) throws ScheduleException {
		logger.info("Get schedule by StartTime = {}, EndTime = {}, DayOfWeek = {}", startTime, endTime, dayOfWeek);
		Schedule scheduleResult = repository.findByStartTimeAndEndTimeAndDayOfWeek(startTime, endTime, dayOfWeek)
				.orElseThrow(()-> new ScheduleException("Cann't find schdedule by StartTime = " + startTime + "EndTime = " + endTime + "DayOfWeek = " + dayOfWeek));
		ScheduleDto scheduleDto = mapper.scheduleToScheduleDto(scheduleResult, context);
		logger.info("OUT: result geting schedule = {}", scheduleDto);
		return scheduleDto;
	}
}
