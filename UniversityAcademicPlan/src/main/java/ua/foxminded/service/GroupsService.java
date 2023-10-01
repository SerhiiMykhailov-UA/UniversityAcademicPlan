package ua.foxminded.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.entity.Groups;
import ua.foxminded.exceptions.GroupsException;
import ua.foxminded.repository.GroupsJPARepository;

@Service
@Transactional(readOnly = true)
public class GroupsService {

	private final GroupsJPARepository repository;
	private final Logger logger = LogManager.getLogger();
	
	public GroupsService(GroupsJPARepository repository) {
		this.repository = repository;
	}
	
	public Groups get(long id) throws GroupsException {
		logger.info("Get group by id = {}", id);
		Groups groupResult = repository.findById(id)
				.orElseThrow(()-> new GroupsException("Cann't find by id = " + id));
		logger.info("OUT: result get group = {}", groupResult);
		return groupResult;
	}
	
	public Groups getByName(String name) throws GroupsException {
		logger.info("Get group by name = {}", name);
		Groups groupResult = repository.findByName(name)
				.orElseThrow(()-> new GroupsException("Cann't find by name = " + name));
		logger.info("OUT: result geting group = {}", groupResult);
		return groupResult;
	}
	
	public List<Groups> getAll() {
		logger.info("Get all groups");
		List<Groups> groups = repository.findAll();
		logger.info("OUT: result get all groups = {}", groups);
		return groups;
	}
	
	@Transactional(readOnly = false)
	public Groups add(Groups group) {
		logger.info("Add new group = {}", group);
		Groups groupResult = repository.saveAndFlush(group);
		logger.info("OUT result group = {}", groupResult);
		return groupResult;
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
	public Groups update(Groups group) throws GroupsException {
		logger.info("Update group = {}", group);
		Groups groupTemp = repository.findByName(group.getName())
				.orElseThrow(()-> new GroupsException("Cann't find group by name = " + group.getName()));
		groupTemp.setCourse(group.getCourse());
		groupTemp.setStudent(group.getStudent());
		Groups groupResult = repository.saveAndFlush(groupTemp);
		logger.info("OUT result group = {}", groupResult);
		return groupResult;
	}
	
	public boolean ifExistsByName(String name) {
		logger.info("Find group by name = {}", name);
		boolean groupResult = repository.existsByName(name);
		logger.info("OUT: result finding group = {}", groupResult);
		return groupResult;
	}
}
