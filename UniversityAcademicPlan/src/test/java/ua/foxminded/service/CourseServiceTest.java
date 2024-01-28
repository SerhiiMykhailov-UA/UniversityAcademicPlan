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

import ua.foxminded.dto.CourseDto;
import ua.foxminded.dto.LocationDto;
import ua.foxminded.entity.Course;
import ua.foxminded.entity.Groups;
import ua.foxminded.entity.Lecture;
import ua.foxminded.entity.Location;
import ua.foxminded.entity.Schedule;
import ua.foxminded.entity.Student;
import ua.foxminded.entity.Teacher;
import ua.foxminded.exceptions.CourseException;
import ua.foxminded.exceptions.LocationException;
import ua.foxminded.mapper.CourseMapper;
import ua.foxminded.repository.CourseJPARepository;
import ua.foxminded.repository.LocationJPARepository;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

	@InjectMocks
	private CourseService service;
	@Mock
	private CourseJPARepository repository;
	@Mock
	private CourseMapper mapper;
	@Mock
	private LocationJPARepository locationJPARepository;
	
	Course entity;
	CourseDto dto;
	Location location;
	
	{
		location = new Location(1, "location1", Arrays.asList(new Course()));
		entity = new Course("c1");
		entity.setLocation(new Location("location123"));
		entity.setGroups(Arrays.asList(new Groups("gr123")));
		entity.setLecture(Arrays.asList(new Lecture("lecture111")));
		entity.setSchedule(Arrays.asList(new Schedule(LocalTime.parse("09:00:00"), LocalTime.parse("10:00:00"), DayOfWeek.FRIDAY)));
		entity.setStudent(Arrays.asList(new Student("fn1", "ln1")));
		entity.setTeacher(Arrays.asList(new Teacher("fn1", "ln1")));
		dto = new CourseDto("c1");
		dto.setLocation(new LocationDto("locationDto"));
		
	}
	
	@Test
	void testGet() throws CourseException {
		when(repository.findById(Mockito.anyLong())).thenReturn( Optional.of(entity));
		when(mapper.courseToCourseDto(Mockito.any(), Mockito.any())).thenReturn(dto);
		
		service.get((long) 1);
		
		verify(mapper, times(1)).courseToCourseDto(Mockito.any(), Mockito.any());
		verify(repository, times(1)).findById((long) 1);
		
	}

	@Test
	void testGetByName() throws CourseException {
		when(repository.findByName(Mockito.anyString())).thenReturn(Optional.of(entity));
		when(mapper.courseToCourseDto(Mockito.any(), Mockito.any())).thenReturn(dto);
		
		service.getByName("entity");
		
		verify(mapper, times(1)).courseToCourseDto(Mockito.any(), Mockito.any());
		verify(repository, times(1)).findByName(Mockito.anyString());
	}

	@Test
	void testGetAll() {
		when(repository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(entity, entity, entity)));
		when(mapper.courseToCourseDto(Mockito.any(), Mockito.any())).thenReturn(new CourseDto("c1"));
		
		service.getAll();
		
		verify(repository, times(1)).findAll();
		verify(mapper, times(3)).courseToCourseDto(Mockito.any(), Mockito.any());
	}

	@Test
	void testAdd() throws LocationException {
		when(mapper.courseDtoToCourse(Mockito.any(), Mockito.any())).thenReturn(entity);
		when(locationJPARepository.findByName(Mockito.anyString())).thenReturn(Optional.of(new Location("location1")));
		when(repository.saveAndFlush(Mockito.any())).thenReturn(entity);
		when(mapper.courseToCourseDto(Mockito.any(), Mockito.any())).thenReturn(dto);
		
		service.add(dto);
		
		verify(mapper, times(1)).courseDtoToCourse(Mockito.any(), Mockito.any());
		verify(repository, times(1)).saveAndFlush(Mockito.any());
		verify(mapper, times(1)).courseToCourseDto(Mockito.any(), Mockito.any());
	}

	@Test
	void testDelete() {
		when(repository.existsById(Mockito.anyLong())).thenReturn(true);
		
		service.delete((long)1);
		
		verify(repository, times(1)).existsById(Mockito.anyLong());
		verify(repository, times(1)).deleteById(Mockito.anyLong());
	}

	@Test
	void testUpdate() throws CourseException, LocationException {
		when(locationJPARepository.findByName(Mockito.anyString())).thenReturn(Optional.of(location));
		when(mapper.courseDtoToCourse(Mockito.any(), Mockito.any())).thenReturn(entity);		
		when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(entity));
		when(repository.save(Mockito.any())).thenReturn(entity);
		when(mapper.courseToCourseDto(Mockito.any(), Mockito.any())).thenReturn(dto);
		
		service.update(dto);
		
		verify(mapper, times(1)).courseDtoToCourse(Mockito.any(), Mockito.any());
		verify(repository, times(1)).findById(Mockito.anyLong());
		verify(repository, times(1)).save(Mockito.any());
		verify(mapper, times(1)).courseToCourseDto(Mockito.any(), Mockito.any());
	}

	@Test
	void testIfExistsByName() {
		when(repository.existsByName(Mockito.anyString())).thenReturn(true);
		
		service.ifExistsByName("c1");
		
		verify(repository, times(1)).existsByName(Mockito.anyString());
	}

}
