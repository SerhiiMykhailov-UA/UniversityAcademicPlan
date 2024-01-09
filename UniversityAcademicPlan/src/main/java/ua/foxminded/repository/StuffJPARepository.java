package ua.foxminded.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.foxminded.entity.Stuff;

public interface StuffJPARepository extends JpaRepository<Stuff, Long> {

	boolean existsByFirstNameAndLastName(String firstName, String lastName);
	
	Optional<Stuff> findByFirstNameAndLastName (String firstName, String lastName);
}
