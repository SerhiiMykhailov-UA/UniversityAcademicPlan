package ua.foxminded.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.dto.AdminDto;
import ua.foxminded.entity.Admin;
import ua.foxminded.exceptions.AdminException;
import ua.foxminded.mapper.AdminMapper;
import ua.foxminded.mapper.CycleAvoidingMappingContext;
import ua.foxminded.repository.AdminJPARepository;

@Service
@Transactional(readOnly = true)
public class AdminService {
	
	private final AdminMapper mapper;
	private final AdminJPARepository repository;
	private PasswordEncoder passwordEncoder;
	private final Logger logger = LogManager.getLogger();
	private CycleAvoidingMappingContext context = new CycleAvoidingMappingContext();
	
	public AdminService(AdminMapper mapper, AdminJPARepository repository, PasswordEncoder passwordEncoder) {
		this.mapper = mapper;
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional(readOnly = false)
	public AdminDto add(AdminDto adminDto) {
		logger.info("Add new Admin = {}", adminDto);
		Admin adminDao = mapper.adminDtoToAdmin(adminDto, context);
		adminDao.setPassword(passwordEncoder.encode(adminDto.getPassword()));
		Admin adminResult = repository.saveAndFlush(adminDao);
		AdminDto adminDtoResult = mapper.adminToAdminDto(adminResult, context);
		logger.info("OUT. Added new Admin = {}", adminDtoResult);
		logger.info("---------------------------------------------------------");
		return adminDtoResult;
	}
	
	public List<AdminDto> getAll(){
		logger.info("Get all admins");
		List<AdminDto> adminDtoList = repository.findAll()
				.stream()
				.map(el->mapper.adminToAdminDto(el, context))
				.collect(Collectors.toList());
		logger.info("OUT. adminList = {}", adminDtoList);
		return adminDtoList;
	}
	
	public AdminDto get(Long id) throws AdminException {
		logger.info("Get admin by ID = {}", id);
		Admin adminDao = repository.findById(id)
				.orElseThrow(()-> new AdminException("Can't find admin by id = " + id));
		AdminDto adminDto = mapper.adminToAdminDto(adminDao, context);
		logger.info("OUT admin = {}", adminDto);
		logger.info("------------------------------------------------");
		return adminDto;
	}
	
	@Transactional(readOnly = false)
	public boolean delete(Long id) {
		logger.info("Delet admin by id = {}", id);
		if (repository.existsById(id)) {
			repository.deleteById(id);
			logger.info("Delet admin by id = {}", true);
			logger.info("-------------------------------------------------------");
			return true;
		}else {
			logger.info("Delet admin by id = {}. Admin isn't exist", false);
			logger.info("-------------------------------------------------------");
			return false;
		}

	}
	
	@Transactional(readOnly = false)
	public AdminDto update(AdminDto adminDto) throws AdminException {
		logger.info("Update admin = {}", adminDto);
		Admin adminDao = mapper.adminDtoToAdmin(adminDto, context);
		Admin adminTemp = repository.findById(adminDao.getId())
				.orElseThrow(()-> new AdminException("Cann't find Admin = " + adminDto));
		if (adminDto.getFirstName() != null && !adminTemp.getFirstName().equals(adminDao.getFirstName()))
			adminTemp.setFirstName(adminDao.getFirstName());
		if (adminDto.getLastName() != null && !adminTemp.getLastName().equals(adminDao.getLastName()))
			adminTemp.setLastName(adminDao.getLastName());
		if (adminDto.getPassword() != null && !adminTemp.getPassword().equals(adminDao.getPassword()))
			adminTemp.setPassword(passwordEncoder.encode(adminDao.getPassword()));
		Admin adminResult = repository.saveAndFlush(adminTemp);
		AdminDto adminDtoResult = mapper.adminToAdminDto(adminResult, context);
		logger.info("OUT. Update admin = {}", adminDtoResult);
		logger.info("------------------------------------------------");
		return adminDtoResult;
	}
	
	public boolean isExistByFirstNameAndLastName(String firstName, String lastName) {
		logger.info("Is admin exist by first name = {} and last name = {}", firstName, lastName);
		boolean adminResult = repository.existsByFirstNameAndLastName(firstName, lastName);
		logger.info("OUT : admin exists {}", adminResult);
		logger.info("------------------------------------------------");
		return adminResult;
	}
	
	public boolean isExistByid(Long id) {
		logger.info("Is admin exist by id = {}", id);
		boolean isExistById = repository.existsById(id);
		logger.info("OUT : admin exists = {}", isExistById);
		logger.info("------------------------------------------------");
		return isExistById;
	}
	
	public AdminDto getByFirstNameAndLastName (String firstName, String lastName) throws AdminException {
		logger.info("Get admin by first name = {} and last name = {}", firstName, lastName);
		Admin adminDao = repository.findByFirstNameAndLastName(firstName, lastName)
				.orElseThrow(()->new AdminException("Admin first name = " + firstName + " last name = " + lastName));
		AdminDto adminDtoResult = mapper.adminToAdminDto(adminDao, context);
		logger.info("OUT : admin {}", adminDtoResult);
		logger.info("------------------------------------------------");
		return adminDtoResult;
	}
}
