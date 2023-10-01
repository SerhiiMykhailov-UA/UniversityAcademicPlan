package ua.foxminded.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.entity.Users;
import ua.foxminded.exceptions.UsersException;
import ua.foxminded.repository.UsersJPARepository;

@Service
@Transactional(readOnly = true)
public class UsersService {

	private UsersJPARepository repository;
	private final Logger logger = LogManager.getLogger();

	public UsersService(UsersJPARepository repository) {
		this.repository = repository;
	}

	public Users get(long id) throws UsersException {
		logger.info("findById (int id) id = {}", id);
		Users userResult = repository.findById(id)
				.orElseThrow(() -> new UsersException("Cann't find User Id: " + id));
		logger.info("Find User result = {}", userResult);
		logger.info("------------------------------------------------------");
		return userResult;
	}
	
	public Users getByNickName(String nickName) throws UsersException {
		logger.info("Find by nickname = {}",  nickName);
		Users userResult = repository.findByNickName(nickName)
				.orElseThrow(() -> new UsersException("Cann't find User nickName: " + nickName));
		logger.info("Find User result = {}", userResult);
		logger.info("------------------------------------------------------");
		return userResult;
	}
	
	public List<Users> getAll() {
		logger.info("Get all Users");
		List<Users> usersList = repository.findAll();
		logger.info("OUT Users list = {}", usersList);
		logger.info("------------------------------------------------------");
		return usersList;
	}

	@Transactional(readOnly = false)
	public Users add(Users user) {
		logger.info("Add user. IN User = {}", user);
		Users userResult = repository.saveAndFlush(user);
		logger.info("OUT result User = {}", userResult);
		logger.info("------------------------------------------------------");
		return userResult;
	}

	@Transactional(readOnly = false)
	public Users update(Users user) {
		logger.info("IN User = {}", user);
		Users userResult = repository.saveAndFlush(user);
		logger.info("OUT User = {}", userResult);
		logger.info("------------------------------------------------------");
		return userResult;
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
	
	public boolean ifExistsByNickName(String nickName) {
		logger.info("Check if User exists by nickname = {}", nickName);
		boolean userResult = repository.existsByNickName(nickName);
		logger.info("OUT boolean = {}", userResult);
		logger.info("------------------------------------------------------");
		return userResult;
	}
}
