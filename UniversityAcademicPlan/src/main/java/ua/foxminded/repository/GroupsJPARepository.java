package ua.foxminded.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.foxminded.entity.Groups;

public interface GroupsJPARepository extends JpaRepository<Groups, Long> {
	
	boolean existsByName (String name);
	
	Optional<Groups> findByName (String name);
}
