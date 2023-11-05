package ua.foxminded.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.foxminded.dto.StudentDto;
import ua.foxminded.entity.Student;
import ua.foxminded.exceptions.StudentException;
import ua.foxminded.mapper.StudentMapper;
import ua.foxminded.repository.StudentJPARepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
	
	@InjectMocks
	StudentService service;
	@Mock
	StudentJPARepository repository;
	@Mock
	StudentMapper mapper;
	
	Student entity;
	StudentDto dto;
	{
		entity = new Student("fn1", "ln1");
		dto = new StudentDto("fn1", "ln1");
	}

	@Test
	void testAdd() {
		when(mapper.studentDtoToStudent(Mockito.any(), Mockito.any())).thenReturn(entity);
		when(repository.saveAndFlush(Mockito.any())).thenReturn(entity);
		when(mapper.studentToStudentDto(Mockito.any(), Mockito.any())).thenReturn(dto);
		
		service.add(dto);
		
		verify(mapper, times(1)).studentDtoToStudent(Mockito.any(), Mockito.any());
		verify(repository, times(1)).saveAndFlush(Mockito.any());
		verify(mapper, times(1)).studentToStudentDto(Mockito.any(), Mockito.any());
	}

	@Test
	void testGetAll() {
		when(repository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(entity, entity, entity)));
		when(mapper.studentToStudentDto(Mockito.any(), Mockito.any())).thenReturn(dto);
		
		service.getAll();
		
		verify(repository, times(1)).findAll();
		verify(mapper, times(3)).studentToStudentDto(Mockito.any(), Mockito.any());
	}

	@Test
	void testGet() throws StudentException {
		when(repository.findById(Mockito.anyLong())).thenReturn( Optional.of(entity));
		when(mapper.studentToStudentDto(Mockito.any(), Mockito.any())).thenReturn(dto);
		
		service.get((long) 1);
		
		verify(mapper, times(1)).studentToStudentDto(Mockito.any(), Mockito.any());
		verify(repository, times(1)).findById((long) 1);
	}

	@Test
	void testDelete() {
		when(repository.existsById(Mockito.anyLong())).thenReturn(true);
		
		service.delete((long)1);
		
		verify(repository, times(1)).existsById(Mockito.anyLong());
		verify(repository, times(1)).deleteById(Mockito.anyLong());
	}

	@Test
	void testUpdate() {
		when(repository.existsById(Mockito.anyLong())).thenReturn(true);
		
		service.delete((long)1);
		
		verify(repository, times(1)).existsById(Mockito.anyLong());
		verify(repository, times(1)).deleteById(Mockito.anyLong());
	}

	@Test
	void testIsExistByFirstNameAndLastName() {
		when(repository.existsByFirstNameAndLastName(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		
		service.isExistByFirstNameAndLastName("fn1", "ln1");
		
		verify(repository, times(1)).existsByFirstNameAndLastName(Mockito.anyString(), Mockito.anyString());
	}

	@Test
	void testGetByFirstNameAndLastName() throws StudentException {
		when(repository.findByFirstNameAndLastName(Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.of(entity));
		when(mapper.studentToStudentDto(Mockito.any(), Mockito.any())).thenReturn(dto);
		
		service.getByFirstNameAndLastName("fn1", "ln1");
		
		verify(mapper, times(1)).studentToStudentDto(Mockito.any(), Mockito.any());
		verify(repository, times(1)).findByFirstNameAndLastName(Mockito.anyString(), Mockito.anyString());
	}

}
