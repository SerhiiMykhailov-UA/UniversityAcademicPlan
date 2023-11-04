package ua.foxminded.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.dto.UsersDto;
import ua.foxminded.entity.Users;
import ua.foxminded.exceptions.UsersException;
import ua.foxminded.mapper.CycleAvoidingMappingContext;
import ua.foxminded.mapper.UsersMapper;
import ua.foxminded.repository.UsersJPARepository;

@Service
@Transactional(readOnly = true)
public class UsersService {

	private UsersMapper mapper;
	private UsersJPARepository repository;
	private final Logger logger = LogManager.getLogger();
	private CycleAvoidingMappingContext context = new CycleAvoidingMappingContext();

	public UsersService(UsersJPARepository repository, UsersMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	public UsersDto get(long id) throws UsersException {
		logger.info("findById (int id) id = {}", id);
		Users userResult = repository.findById(id)
				.orElseThrow(() -> new UsersException("Cann't find User Id: " + id));
		UsersDto userDto = mapper.usersToUsersDto(userResult, context);
		logger.info("Find User result = {}", userResult);
		logger.info("------------------------------------------------------");
		return userDto;
	}
	
	public UsersDto getByNickName(String nickName) throws UsersException {
		logger.info("Find by nickname = {}",  nickName);
		Users userResult = repository.findByNickName(nickName)
				.orElseThrow(() -> new UsersException("Cann't find User nickName: " + nickName));
		UsersDto userDto = mapper.usersToUsersDto(userResult, context);
		logger.info("Find User result = {}", userResult);
		logger.info("------------------------------------------------------");
		return userDto;
	}
	
	public List<UsersDto> getAll() {
		logger.info("Get all Users");
		List<UsersDto> usersList = repository.findAll().stream().map(el -> mapper.usersToUsersDto(el, context)).collect(Collectors.toList());
		logger.info("OUT Users list = {}", usersList);
		logger.info("------------------------------------------------------");
		return usersList;
	}

	@Transactional(readOnly = false)
	public UsersDto add(UsersDto user) {
		logger.info("Add user. IN User = {}", user);
		Users userDao = mapper.usersDtoToUsers(user, context);
		Users userResult = repository.saveAndFlush(userDao);
		UsersDto userDto = mapper.usersToUsersDto(userResult, context);
		logger.info("OUT result User = {}", userResult);
		logger.info("------------------------------------------------------");
		return userDto;
	}

	@Transactional(readOnly = false)
	public UsersDto update(UsersDto user) {
		logger.info("IN User = {}", user);
		Users userDao = mapper.usersDtoToUsers(user, context);
		Users userResult = repository.saveAndFlush(userDao);
		UsersDto userDto = mapper.usersToUsersDto(userResult, context);
		logger.info("OUT User = {}", userResult);
		logger.info("------------------------------------------------------");
		return userDto;
	}
	
	@Transactional(readOnly = false)
	public boolean delete(long id) {
		logger.info("IN Id = {}", id);
		if (repository.existsById(id)) {
			repository.deleteById(id);
			logger.info("OUT user was successfully delete");
			logger.info("------------------------------------------------------");
			return true;
		} else {
			logger.info("OUT user doesn't exists");
			logger.info("------------------------------------------------------");
			return false;
		}
	}
	
	public boolean isExistsByNickName(String nickName) {
		logger.info("Check if User exists by nickname = {}", nickName);
		boolean userResult = repository.existsByNickName(nickName);
		logger.info("OUT boolean = {}", userResult);
		logger.info("------------------------------------------------------");
		return userResult;
	}
}
