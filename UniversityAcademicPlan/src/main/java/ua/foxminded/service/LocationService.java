package ua.foxminded.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.dto.LocationDto;
import ua.foxminded.entity.Location;
import ua.foxminded.exceptions.LocationException;
import ua.foxminded.mapper.CycleAvoidingMappingContext;
import ua.foxminded.mapper.LocationMapper;
import ua.foxminded.repository.LocationJPARepository;

@Service
@Transactional(readOnly = true)
public class LocationService {

	private LocationMapper mapper;
	private LocationJPARepository repository;
	private final Logger logger = LogManager.getLogger();
	private CycleAvoidingMappingContext context = new CycleAvoidingMappingContext();
	public LocationService(LocationJPARepository repository, LocationMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}
	
	public LocationDto get(long id) throws LocationException {
		logger.info("Get location by id = {}", id);
		Location locationResult = repository.findById(id)
				.orElseThrow(()-> new LocationException("Cann't find by id = " + id));
		LocationDto locationDto = mapper.locationToLocationDto(locationResult, context);
		logger.info("OUT: result get location = {}", locationDto);
		return locationDto;
	}
	
	public LocationDto getByName(String name) throws LocationException {
		logger.info("Get location by name = {}", name);
		Location locationResult = repository.findByName(name)
				.orElseThrow(()-> new LocationException("Cann't find by name = " + name));
		LocationDto locationDto = mapper.locationToLocationDto(locationResult, context);
		logger.info("OUT: result geting location = {}", locationDto);
		return locationDto;
	}
	
	public List<LocationDto> getAll() {
		logger.info("Get all location");
		List<LocationDto> locations = repository.findAll()
				.stream().map(el -> mapper.locationToLocationDto(el, context)).collect(Collectors.toList());
		logger.info("OUT: result get all locations = {}", locations);
		return locations;
	}
	
	@Transactional(readOnly = false)
	public LocationDto add(LocationDto location) {
		logger.info("Add new location = {}", location);
		Location locationDao = mapper.locationDtoToLocation(location, context);
		Location locationResult = repository.saveAndFlush(locationDao);
		LocationDto locationDto = mapper.locationToLocationDto(locationResult, context);
		logger.info("OUT result location = {}", locationDto);
		return locationDto;
	}
	
	@Transactional(readOnly = false)
	public boolean delete(long id) {
		logger.info("Delete location by id = {}", id);
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
	public LocationDto update(LocationDto location) throws LocationException {
		logger.info("Update location = {}", location);
		Location locationDao = mapper.locationDtoToLocation(location, context);
		Location locationTemp = repository.findByName(locationDao.getName())
				.orElseThrow(()-> new LocationException("Cann't find location by name = " + locationDao.getName()));
		locationTemp.setCourse(locationDao.getCourse());
		Location locationResult = repository.saveAndFlush(locationTemp);
		LocationDto locationDto = mapper.locationToLocationDto(locationResult, context);
		logger.info("OUT result location = {}", locationDto);
		return locationDto;
	}
	
	public boolean ifExistsByName(String name) {
		logger.info("Find location by name = {}", name);
		boolean locationResult = repository.existsByName(name);
		logger.info("OUT: result finding location = {}", locationResult);
		return locationResult;
	}
}
