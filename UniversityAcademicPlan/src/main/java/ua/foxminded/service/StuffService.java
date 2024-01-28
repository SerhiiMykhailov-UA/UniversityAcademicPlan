package ua.foxminded.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.dto.StuffDto;
import ua.foxminded.entity.Stuff;
import ua.foxminded.exceptions.StuffException;
import ua.foxminded.mapper.CycleAvoidingMappingContext;
import ua.foxminded.mapper.StuffMapper;
import ua.foxminded.repository.StuffJPARepository;

@Service
@Transactional
public class StuffService {
	
	private final StuffMapper mapper;
	private final StuffJPARepository repository;
	private PasswordEncoder passwordEncoder;
	private final Logger logger = LogManager.getLogger();
	private final CycleAvoidingMappingContext context = new CycleAvoidingMappingContext();

	public StuffService(StuffMapper mapper, StuffJPARepository repository, PasswordEncoder passwordEncoder) {
		this.mapper = mapper;
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Transactional(readOnly = false)
	public StuffDto add (StuffDto stuffDto) {
		logger.info("Add new Stuff = {}", stuffDto);
		Stuff stuffDao = mapper.stuffDtoToStuff(stuffDto, context);
		stuffDao.setPassword(passwordEncoder.encode(stuffDto.getPassword()));
		Stuff stuffResult = repository.saveAndFlush(stuffDao);
		StuffDto stuffDtoResult = mapper.stuffToStuffDto(stuffResult, context);
		logger.info("OUT. Added new Stuff = {}", stuffDtoResult);
		logger.info("---------------------------------------------------------");
		return stuffDtoResult;
	}

	public List<StuffDto> getAll(){
		logger.info("Get all Stuff");
		List<StuffDto> stuffDtoList = repository.findAll()
				.stream()
				.map(el->mapper.stuffToStuffDto(el, context))
				.collect(Collectors.toList());
		logger.info("OUT stuffDtoList = {}", stuffDtoList);
		return stuffDtoList;
	}
	
	public StuffDto get (Long id) throws StuffException {
		logger.info("Get stuff by id = {}", id);
		Stuff stuff = repository.findById(id)
				.orElseThrow(()-> new StuffException("Cann't find stuff by id = " + id));
		StuffDto stuffDto = mapper.stuffToStuffDto(stuff, context);
		logger.info("OUT stuff = {}", stuffDto);
		return stuffDto;
	}
	
	@Transactional(readOnly = false)
	public boolean delete(Long id) {
		logger.info("Delet stuff by id = {}", id);
		if (repository.existsById(id)) {
			repository.deleteById(id);
			if (!repository.existsById(id)) {
				return true;
			}else {
				logger.warn("Error delet stuff id = {}", id);
				logger.info("-------------------------------------------------------");
				return false;
			}
		}else {
			logger.info("Delet stuff by id = {}. Stuff isn't exist", false);
			logger.info("-------------------------------------------------------");
			return false;
		}
	}
	
	@Transactional(readOnly = false)
	public StuffDto update(StuffDto stuffDto) throws StuffException {
		logger.info("Update stuff = {}", stuffDto);
		Stuff stuffDao = mapper.stuffDtoToStuff(stuffDto, context);
		Stuff stuffTemp = repository.findById(stuffDao.getId())
				.orElseThrow(()-> new StuffException("Cann't find stuff = " + stuffDto));
		if (stuffDto.getFirstName() != null && !stuffTemp.getFirstName().equals(stuffDao.getFirstName()))
			stuffTemp.setFirstName(stuffDao.getFirstName());
		if (stuffDto.getLastName() != null && !stuffTemp.getLastName().equals(stuffDao.getLastName()))
			stuffTemp.setLastName(stuffDao.getLastName());
		if (stuffDto.getPassword() != null && !stuffTemp.getPassword().equals(stuffDao.getPassword()))
			stuffTemp.setPassword(passwordEncoder.encode(stuffDao.getPassword()));
		Stuff stuffDaoResult = repository.saveAndFlush(stuffTemp);
		StuffDto stuffDtoResult = mapper.stuffToStuffDto(stuffDaoResult, context);
		logger.info("OUT update stuff = {}", stuffDaoResult);
		logger.info("------------------------------------------------");
		return stuffDtoResult;
	}
	
	public boolean isExistByFirstNameAndLastName(String firstName, String lastName) {
		logger.info("Is stuff exist by first name = {} and last name = {}", firstName, lastName);
		boolean stuffResult = repository.existsByFirstNameAndLastName(firstName, lastName);
		logger.info("OUT : stuff exists {}", stuffResult);
		logger.info("------------------------------------------------");
		return stuffResult;
	}
	
	public boolean isExistByid(Long id) {
		logger.info("Is stuff exist by id = {}", id);
		boolean isExistById = repository.existsById(id);
		logger.info("OUT : stuff exists = {}", isExistById);
		logger.info("------------------------------------------------");
		return isExistById;
	}
	
	public StuffDto getByFirstNameAndLastName(String firstName, String lastName) throws StuffException {
		logger.info("Get stuff by first name = {} and last name = {}", firstName, lastName);
		Stuff stuffDao = repository.findByFirstNameAndLastName(firstName, lastName)
				.orElseThrow(()->new StuffException("Admin first name = " + firstName + " last name = " + lastName));
		StuffDto stuffDtoResult = mapper.stuffToStuffDto(stuffDao, context);
		logger.info("OUT : admin {}", stuffDtoResult);
		logger.info("------------------------------------------------");
		return stuffDtoResult;
	}
}
