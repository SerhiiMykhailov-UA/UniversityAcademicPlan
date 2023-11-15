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

import ua.foxminded.dto.UsersDto;
import ua.foxminded.entity.UserType;
import ua.foxminded.entity.Users;
import ua.foxminded.exceptions.UsersException;
import ua.foxminded.mapper.UsersMapper;
import ua.foxminded.repository.UsersJPARepository;

@ExtendWith(MockitoExtension.class)
class UsersServiceTest {
	
	@InjectMocks
	UsersService service;
	@Mock
	UsersJPARepository repository;
	@Mock
	UsersMapper mapper;
	
	Users entity;
	UsersDto dto;
	{
		entity = new Users("nickname", "password");
		dto = new UsersDto("nickname", "password");
	}

	@Test
	void testGet() throws UsersException {
		when(repository.findById(Mockito.anyLong())).thenReturn( Optional.of(entity));
		when(mapper.usersToUsersDto(Mockito.any(), Mockito.any())).thenReturn(dto);
		
		service.get((long) 1);
		
		verify(mapper, times(1)).usersToUsersDto(Mockito.any(), Mockito.any());
		verify(repository, times(1)).findById((long) 1);
	}

	@Test
	void testGetByNickName() throws UsersException {
		when(repository.findByNickName(Mockito.anyString())).thenReturn(Optional.of(entity));
		when(mapper.usersToUsersDto(Mockito.any(), Mockito.any())).thenReturn(dto);
		
		service.getByNickName("nickname");
		
		verify(mapper, times(1)).usersToUsersDto(Mockito.any(), Mockito.any());
		verify(repository, times(1)).findByNickName("nickname");
	}

	@Test
	void testGetAll() {
		when(repository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(entity, entity, entity)));
		when(mapper.usersToUsersDto(Mockito.any(), Mockito.any())).thenReturn(dto);
		
		service.getAll();
		
		verify(repository, times(1)).findAll();
		verify(mapper, times(3)).usersToUsersDto(Mockito.any(), Mockito.any());
	}

	@Test
	void testAdd() {
		when(mapper.usersDtoToUsers(Mockito.any(), Mockito.any())).thenReturn(entity);
		when(repository.saveAndFlush(Mockito.any())).thenReturn(entity);
		when(mapper.usersToUsersDto(Mockito.any(), Mockito.any())).thenReturn(dto);
		
		service.add(dto);
		
		verify(mapper, times(1)).usersDtoToUsers(Mockito.any(), Mockito.any());
		verify(repository, times(1)).saveAndFlush(Mockito.any());
		verify(mapper, times(1)).usersToUsersDto(Mockito.any(), Mockito.any());
	}

	@Test
	void testUpdate() {
		when(repository.existsById(Mockito.anyLong())).thenReturn(true);
		
		service.delete((long)1);
		
		verify(repository, times(1)).existsById(Mockito.anyLong());
		verify(repository, times(1)).deleteById(Mockito.anyLong());
	}

	@Test
	void testDelete() {
		when(repository.existsById(Mockito.anyLong())).thenReturn(true);
		
		service.delete((long)1);
		
		verify(repository, times(1)).existsById(Mockito.anyLong());
		verify(repository, times(1)).deleteById(Mockito.anyLong());
	}

	@Test
	void testIfExistsByNickName() {
		when(repository.existsByNickName(Mockito.anyString())).thenReturn(true);
		
		service.isExistsByNickName("fn1");
		
		verify(repository, times(1)).existsByNickName(Mockito.anyString());
	}

}
