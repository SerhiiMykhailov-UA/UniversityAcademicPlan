package ua.foxminded.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.dto.GroupsDto;
import ua.foxminded.entity.Groups;
import ua.foxminded.exceptions.GroupsException;
import ua.foxminded.mapper.GroupsMapper;
import ua.foxminded.repository.GroupsJPARepository;

@Service
@Transactional(readOnly = true)
public class GroupsService {

	private GroupsMapper mapper; 
	private GroupsJPARepository repository;
	private final Logger logger = LogManager.getLogger();
	
	public GroupsService(GroupsJPARepository repository, GroupsMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}
	
	public GroupsDto get(long id) throws GroupsException {
		logger.info("Get group by id = {}", id);
		Groups groupResult = repository.findById(id)
				.orElseThrow(()-> new GroupsException("Cann't find by id = " + id));
		GroupsDto groupDto = mapper.groupsToGroupsDto(groupResult);
		logger.info("OUT: result get group = {}", groupDto);
		return groupDto;
	}
	
	public GroupsDto getByName(String name) throws GroupsException {
		logger.info("Get group by name = {}", name);
		Groups groupResult = repository.findByName(name)
				.orElseThrow(()-> new GroupsException("Cann't find by name = " + name));
		GroupsDto groupDto = mapper.groupsToGroupsDto(groupResult);
		logger.info("OUT: result geting group = {}", groupDto);
		return groupDto;
	}
	
	public List<GroupsDto> getAll() {
		logger.info("Get all groups");
		List<GroupsDto> groups = repository.findAll()
				.stream().map(mapper::groupsToGroupsDto).collect(Collectors.toList());
		logger.info("OUT: result get all groups = {}", groups);
		return groups;
	}
	
	@Transactional(readOnly = false)
	public GroupsDto add(GroupsDto group) {
		logger.info("Add new group = {}", group);
		Groups groupDao = mapper.groupsDtoToGroups(group);
		Groups groupResult = repository.saveAndFlush(groupDao);
		GroupsDto groupDto = mapper.groupsToGroupsDto(groupResult);
		logger.info("OUT result group = {}", groupDto);
		return groupDto;
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
	public GroupsDto update(GroupsDto group) throws GroupsException {
		logger.info("Update group = {}", group);
		Groups groupDao = mapper.groupsDtoToGroups(group);
		Groups groupTemp = repository.findByName(groupDao.getName())
				.orElseThrow(()-> new GroupsException("Cann't find group by name = " + groupDao.getName()));
		groupTemp.setCourse(groupDao.getCourse());
		groupTemp.setStudent(groupDao.getStudent());
		Groups groupResult = repository.saveAndFlush(groupTemp);
		GroupsDto groupDto = mapper.groupsToGroupsDto(groupResult);
		logger.info("OUT result group = {}", groupDto);
		return groupDto;
	}
	
	public boolean ifExistsByName(String name) {
		logger.info("Find group by name = {}", name);
		boolean groupResult = repository.existsByName(name);
		logger.info("OUT: result finding group = {}", groupResult);
		return groupResult;
	}
}
