package ua.foxminded.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.foxminded.entity.Student;

public interface StudentJPARepository extends JpaRepository<Student, Long> {
	
	boolean existsByFirstNameAndLastName (String firstName, String lastName);
	
	Optional<Student> findByFirstNameAndLastName (String firstName, String lastName);
}
