package ua.foxminded.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.foxminded.entity.Teacher;

public interface TeacherJPARepository extends JpaRepository<Teacher, Long> {

	boolean existsByFirstNameAndLastName (String firstName, String lastName);
	
	Optional<Teacher> findByFirstNameAndLastName (String firstName, String lastName);
}
