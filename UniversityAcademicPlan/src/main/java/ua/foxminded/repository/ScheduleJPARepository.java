package ua.foxminded.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.foxminded.entity.Schedule;

public interface ScheduleJPARepository extends JpaRepository<Schedule, Integer> {

}
