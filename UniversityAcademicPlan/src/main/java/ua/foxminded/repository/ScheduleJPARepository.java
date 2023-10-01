package ua.foxminded.repository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.foxminded.entity.Schedule;

public interface ScheduleJPARepository extends JpaRepository<Schedule, Long> {

	boolean existsByStartTimeAndEndTimeAndDayOfWeek (LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek);
	
	Optional<Schedule> findByStartTimeAndEndTimeAndDayOfWeek (LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek);
}
