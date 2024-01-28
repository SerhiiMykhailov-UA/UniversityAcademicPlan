package ua.foxminded.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.foxminded.dto.ScheduleDto;
import ua.foxminded.entity.Course;
import ua.foxminded.entity.Schedule;
import ua.foxminded.exceptions.ScheduleException;
import ua.foxminded.mapper.ScheduleMapper;
import ua.foxminded.repository.ScheduleJPARepository;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {
	
	@InjectMocks
	ScheduleService service;
	@Mock
	ScheduleJPARepository repository;
	@Mock
	ScheduleMapper mapper;
	
	Schedule entity;
	ScheduleDto dto;
	
	{
		entity = new Schedule(LocalTime.of(8, 0), LocalTime.of(9, 40), DayOfWeek.MONDAY);
		entity.setCourse(Arrays.asList(new Course("c1")));
		dto = new ScheduleDto(LocalTime.of(8, 0), LocalTime.of(9, 40), DayOfWeek.MONDAY);
	}
	
	@Test
	void testGet() throws ScheduleException {
		when(repository.findById(Mockito.anyLong())).thenReturn( Optional.of(entity));
		when(mapper.scheduleToScheduleDto(Mockito.any(), Mockito.any())).thenReturn(dto);
		
		service.get((long) 1);
		
		verify(mapper, times(1)).scheduleToScheduleDto(Mockito.any(), Mockito.any());
		verify(repository, times(1)).findById((long) 1);
		
	}

	@Test
	void testGetByStartTimeAndEndTimeAndDayOfWeek() throws ScheduleException {
		when(repository.findByStartTimeAndEndTimeAndDayOfWeek(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Optional.of(entity));
		when(mapper.scheduleToScheduleDto(Mockito.any(), Mockito.any())).thenReturn(dto);
		
		service.getByStartTimeAndEndTimeAndDayOfWeek(LocalTime.of(8, 0), LocalTime.of(9, 40), DayOfWeek.MONDAY);
		
		verify(mapper, times(1)).scheduleToScheduleDto(Mockito.any(), Mockito.any());
		verify(repository, times(1)).findByStartTimeAndEndTimeAndDayOfWeek(Mockito.any(), Mockito.any(), Mockito.any());
	}

	@Test
	void testGetAll() {
		when(repository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(entity, entity, entity)));
		when(mapper.scheduleToScheduleDto(Mockito.any(), Mockito.any())).thenReturn(dto);
		
		service.getAll();
		
		verify(repository, times(1)).findAll();
		verify(mapper, times(3)).scheduleToScheduleDto(Mockito.any(), Mockito.any());
	}

	@Test
	void testAdd() {
		when(mapper.scheduleDtoToSchedule(Mockito.any(), Mockito.any())).thenReturn(entity);
		when(repository.saveAndFlush(Mockito.any())).thenReturn(entity);
		when(mapper.scheduleToScheduleDto(Mockito.any(), Mockito.any())).thenReturn(dto);
		
		service.add(dto);
		
		verify(mapper, times(1)).scheduleDtoToSchedule(Mockito.any(), Mockito.any());
		verify(repository, times(1)).saveAndFlush(Mockito.any());
		verify(mapper, times(1)).scheduleToScheduleDto(Mockito.any(), Mockito.any());
	}

	@Test
	void testDelete() {
		when(repository.existsById(Mockito.anyLong())).thenReturn(true);
		
		service.delete((long)1);
		
		verify(repository, times(1)).existsById(Mockito.anyLong());
		verify(repository, times(1)).deleteById(Mockito.anyLong());
	}

	@Test
	void testUpdate() throws ScheduleException {
		when(mapper.scheduleDtoToSchedule(Mockito.any(), Mockito.any())).thenReturn(entity);		
		when(repository.findByStartTimeAndEndTimeAndDayOfWeek(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Optional.of(entity));
		when(repository.saveAndFlush(Mockito.any())).thenReturn(entity);
		when(mapper.scheduleToScheduleDto(Mockito.any(), Mockito.any())).thenReturn(dto);
		
		service.update(dto);
		
		verify(mapper, times(1)).scheduleDtoToSchedule(Mockito.any(), Mockito.any());
		verify(repository, times(1)).findByStartTimeAndEndTimeAndDayOfWeek(Mockito.any(), Mockito.any(), Mockito.any());
		verify(repository, times(1)).saveAndFlush(Mockito.any());
		verify(mapper, times(1)).scheduleToScheduleDto(Mockito.any(), Mockito.any());
	}

	@Test
	void testExistsByStartTimeAndEndTimeAndDayOfWeek() {
		when(repository.existsByStartTimeAndEndTimeAndDayOfWeek(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
		
		service.existsByStartTimeAndEndTimeAndDayOfWeek(LocalTime.of(8, 0), LocalTime.of(9, 40), DayOfWeek.MONDAY);
		
		verify(repository, times(1)).existsByStartTimeAndEndTimeAndDayOfWeek(Mockito.any(), Mockito.any(), Mockito.any());
	}

}
