package ua.foxminded.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ua.foxminded.dto.UsersDto;
import ua.foxminded.entity.Users;
import ua.foxminded.exceptions.UsersException;
import ua.foxminded.mapper.CycleAvoidingMappingContext;
import ua.foxminded.mapper.UsersMapper;
import ua.foxminded.repository.UsersJPARepository;
import ua.foxminded.security.UsersDetails;

@Service
public class UsersDetailsService implements UserDetailsService {

	private final UsersJPARepository repository;
	private final UsersMapper mapper;
	private CycleAvoidingMappingContext context = new CycleAvoidingMappingContext();
	
	public UsersDetailsService(UsersJPARepository repository, UsersMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users usersDao;
		UsersDto usersDto;
		try {
			usersDao = repository.findByNickName(username).orElseThrow(() -> new UsersException("User not found"));
		} catch (UsersException e) {
			throw new UsernameNotFoundException(e.getMessage());
		}
		usersDto = mapper.usersToUsersDto(usersDao, context);
		return new UsersDetails(usersDto);
	}

}
