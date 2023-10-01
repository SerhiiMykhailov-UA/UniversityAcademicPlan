package ua.foxminded.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.foxminded.entity.Location;

public interface LocationJPARepository extends JpaRepository<Location, Long> {

	boolean existsByName (String name);
	
	Optional<Location> findByName (String name);
}
