package ua.foxminded.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.entity.Location;
import ua.foxminded.exceptions.LocationException;
import ua.foxminded.repository.LocationJPARepository;

@Service
@Transactional(readOnly = true)
public class LocationService {

	private final LocationJPARepository repository;
	private final Logger logger = LogManager.getLogger();
	
	public LocationService(LocationJPARepository repository) {
		this.repository = repository;
	}
	
	public Location get(long id) throws LocationException {
		logger.info("Get location by id = {}", id);
		Location locationResult = repository.findById(id)
				.orElseThrow(()-> new LocationException("Cann't find by id = " + id));
		logger.info("OUT: result get location = {}", locationResult);
		return locationResult;
	}
	
	public Location getByName(String name) throws LocationException {
		logger.info("Get location by name = {}", name);
		Location locationResult = repository.findByName(name)
				.orElseThrow(()-> new LocationException("Cann't find by name = " + name));
		logger.info("OUT: result geting location = {}", locationResult);
		return locationResult;
	}
	
	public List<Location> getAll() {
		logger.info("Get all location");
		List<Location> locations = repository.findAll();
		logger.info("OUT: result get all locations = {}", locations);
		return locations;
	}
	
	@Transactional(readOnly = false)
	public Location add(Location location) {
		logger.info("Add new location = {}", location);
		Location locationResult = repository.saveAndFlush(location);
		logger.info("OUT result location = {}", locationResult);
		return locationResult;
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
	public Location update(Location location) throws LocationException {
		logger.info("Update location = {}", location);
		Location locationTemp = repository.findByName(location.getName())
				.orElseThrow(()-> new LocationException("Cann't find location by name = " + location.getName()));
		locationTemp.setCourse(location.getCourse());
		Location locationResult = repository.saveAndFlush(locationTemp);
		logger.info("OUT result location = {}", locationResult);
		return locationResult;
	}
	
	public boolean ifExistsByName(String name) {
		logger.info("Find location by name = {}", name);
		boolean locationResult = repository.existsByName(name);
		logger.info("OUT: result finding location = {}", locationResult);
		return locationResult;
	}
}
