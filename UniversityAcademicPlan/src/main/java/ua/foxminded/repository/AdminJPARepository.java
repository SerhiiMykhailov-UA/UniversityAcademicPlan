package ua.foxminded.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.foxminded.entity.Admin;

public interface AdminJPARepository extends JpaRepository<Admin, Long> {
	
	boolean existsByFirstNameAndLastName (String firstName, String lastName);
	
	Optional<Admin> findByFirstNameAndLastName (String firstName, String lastName);

}
