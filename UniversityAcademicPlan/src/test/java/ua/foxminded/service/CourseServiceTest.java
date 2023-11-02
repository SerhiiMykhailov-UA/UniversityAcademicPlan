package ua.foxminded.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.foxminded.dto.CourseDto;
import ua.foxminded.entity.Course;
import ua.foxminded.exceptions.CourseException;
import ua.foxminded.mapper.CourseMapper;
import ua.foxminded.mapper.CycleAvoidingMappingContext;
import ua.foxminded.repository.CourseJPARepository;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

	@InjectMocks
	private CourseService courseService;
	@Mock
	private CourseJPARepository repository;
	@Mock
	private CourseMapper mapper;
	CycleAvoidingMappingContext context = new CycleAvoidingMappingContext();
	
	@Test
	void testGet() throws CourseException {
		when(repository.findById(Mockito.anyLong())).thenReturn( Optional.of(new Course("c1")));
		when(mapper.courseToCourseDto(Mockito.any(Course.class), Mockito.any(CycleAvoidingMappingContext.class))).thenReturn(new CourseDto("c1"));
		courseService.get((long) 1);
		verify(mapper, times(1)).courseToCourseDto(new Course("c1"), new CycleAvoidingMappingContext());
		verify(repository, times(1)).findById((long) 1);
		
	}

	@Test
	void testGetByName() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAll() {
		fail("Not yet implemented");
	}

	@Test
	void testAdd() {
		fail("Not yet implemented");
	}

	@Test
	void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	void testIfExistsByName() {
		fail("Not yet implemented");
	}

}
