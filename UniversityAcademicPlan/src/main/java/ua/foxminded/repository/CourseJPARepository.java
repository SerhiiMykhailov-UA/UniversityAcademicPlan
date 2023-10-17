package ua.foxminded.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import ua.foxminded.entity.Course;

public interface CourseJPARepository extends JpaRepository<Course, Long> {
	
	boolean existsByName (String name);
	
	Optional<Course> findByName (String name);

}
