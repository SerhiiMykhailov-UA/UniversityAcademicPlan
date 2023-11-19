package ua.foxminded.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
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

	private final UsersMapper mapper;
	private final UsersJPARepository repository;
	private final PasswordEncoder passwordEncoder;
	private final Logger logger = LogManager.getLogger();
	private CycleAvoidingMappingContext context = new CycleAvoidingMappingContext();

	public UsersService(UsersJPARepository repository, UsersMapper mapper, PasswordEncoder passwordEncoder) {
		this.repository = repository;
		this.mapper = mapper;
		this.passwordEncoder = passwordEncoder;
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
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));;
		
		Users userDao = mapper.usersDtoToUsers(user, context);
		Users userResult = repository.saveAndFlush(userDao);
		UsersDto userDto = mapper.usersToUsersDto(userResult, context);
		logger.info("OUT result User = {}", userResult);
		logger.info("------------------------------------------------------");
		return userDto;
	}

	@Transactional(readOnly = false)
	public UsersDto update(UsersDto user) throws UsersException {
		logger.info("IN User = {}", user);
		//Users usersDao = mapper.usersDtoToUsers(user, context);
		Users usersDaoTemp = repository.findById(user.getId()).orElseThrow(()-> new UsersException("User isn't exists"));
		usersDaoTemp.setNickName(user.getNickName());
		usersDaoTemp.setUserType(user.getUserType());
		Users userResult = repository.saveAndFlush(usersDaoTemp);
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
