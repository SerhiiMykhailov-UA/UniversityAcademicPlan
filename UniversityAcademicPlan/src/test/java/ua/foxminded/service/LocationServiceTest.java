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

import ua.foxminded.dto.LocationDto;
import ua.foxminded.entity.Location;
import ua.foxminded.exceptions.LocationException;
import ua.foxminded.mapper.LocationMapper;
import ua.foxminded.repository.LocationJPARepository;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {
	
	@InjectMocks
	LocationService service;
	@Mock
	LocationJPARepository repository;
	@Mock
	LocationMapper mapper;
	
	Location entity;
	LocationDto dto;
	{
		entity = new Location("location1");
		dto = new LocationDto("location1");
	}

	@Test
	void testGet() throws LocationException {
		when(repository.findById(Mockito.anyLong())).thenReturn( Optional.of(entity));
		when(mapper.locationToLocationDto(Mockito.any(), Mockito.any())).thenReturn(dto);
		
		service.get((long) 1);
		
		verify(mapper, times(1)).locationToLocationDto(Mockito.any(), Mockito.any());
		verify(repository, times(1)).findById((long) 1);
		
	}

	@Test
	void testGetByName() throws LocationException {
		when(repository.findByName(Mockito.anyString())).thenReturn(Optional.of(entity));
		when(mapper.locationToLocationDto(Mockito.any(), Mockito.any())).thenReturn(dto);
		
		service.getByName("entity");
		
		verify(mapper, times(1)).locationToLocationDto(Mockito.any(), Mockito.any());
		verify(repository, times(1)).findByName(Mockito.anyString());
	}

	@Test
	void testGetAll() {
		when(repository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(entity, entity, entity)));
		when(mapper.locationToLocationDto(Mockito.any(), Mockito.any())).thenReturn(dto);
		
		service.getAll();
		
		verify(repository, times(1)).findAll();
		verify(mapper, times(3)).locationToLocationDto(Mockito.any(), Mockito.any());
	}

	@Test
	void testAdd() {
		when(mapper.locationDtoToLocation(Mockito.any(), Mockito.any())).thenReturn(entity);
		when(repository.saveAndFlush(Mockito.any())).thenReturn(entity);
		when(mapper.locationToLocationDto(Mockito.any(), Mockito.any())).thenReturn(dto);
		
		service.add(dto);
		
		verify(mapper, times(1)).locationDtoToLocation(Mockito.any(), Mockito.any());
		verify(repository, times(1)).saveAndFlush(Mockito.any());
		verify(mapper, times(1)).locationToLocationDto(Mockito.any(), Mockito.any());
	}

	@Test
	void testDelete() {
		when(repository.existsById(Mockito.anyLong())).thenReturn(true);
		service.delete((long)1);
		verify(repository, times(1)).existsById(Mockito.anyLong());
		verify(repository, times(1)).deleteById(Mockito.anyLong());
	}

	@Test
	void testUpdate() throws LocationException {
		when(mapper.locationDtoToLocation(Mockito.any(), Mockito.any())).thenReturn(entity);		
		when(repository.findByName(Mockito.anyString())).thenReturn(Optional.of(entity));
		when(repository.saveAndFlush(Mockito.any())).thenReturn(entity);
		when(mapper.locationToLocationDto(Mockito.any(), Mockito.any())).thenReturn(dto);
		
		service.update(dto);
		
		verify(mapper, times(1)).locationDtoToLocation(Mockito.any(), Mockito.any());
		verify(repository, times(1)).findByName(Mockito.anyString());
		verify(repository, times(1)).saveAndFlush(Mockito.any());
		verify(mapper, times(1)).locationToLocationDto(Mockito.any(), Mockito.any());
	}

	@Test
	void testIfExistsByName() {
		when(repository.existsByName(Mockito.anyString())).thenReturn(true);
		
		service.ifExistsByName("c1");
		
		verify(repository, times(1)).existsByName(Mockito.anyString());
	}

}
