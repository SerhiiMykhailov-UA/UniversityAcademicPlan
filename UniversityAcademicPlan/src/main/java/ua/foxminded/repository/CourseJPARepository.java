package ua.foxminded.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.foxminded.entity.Course;

public interface CourseJPARepository extends JpaRepository<Course, Long> {
	
	boolean existsByName (String name);
	
	Optional<Course> findByName (String name);

}
