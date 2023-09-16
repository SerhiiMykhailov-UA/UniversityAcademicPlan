package ua.foxminded.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.foxminded.entity.Users;

public interface UsersJPARepository extends JpaRepository<Users, Long> {

	boolean existsByNickName (String nickName);
	
	Optional<Users> findByNickName (String nickName);
	
}
